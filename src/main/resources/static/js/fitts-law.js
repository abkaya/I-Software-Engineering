/* AUTHOR		: Jan Huijghebaert
 * DESCRIPTION	: Fitts Test Javascript
 * OUTPUT		: Throughput, mean time, error's
 */

// ---------- OUTPUT ----------
var outputThroughput = [-1];
var outputMeanTime = [-1];
var outputNumOfErrors = [-1];
// ----------------------------

var testDimension = makeDimension(1140, 650, 30, 30, 30, 30);	// Dimensions of test frame

var fittsTest = {
	target: {x: 0, y: 0, r: 10},	// Target circle (location and radius)
	lastMousePoint: {},				// Last mouse point (location and timestamp)

	arrayTargets: [],				// Array from all the targets (one sequence)
	currentPosition: 0,				// Current position
	currentTarget: 0,				// Current target that is visible

	numOfErrors: 0,					// Number of error's in current sequence
	startTime: 0,					// Timer start time
	stopTime: 0,					// Timer stop time

	currentSequence: 0,				// Current sequence that is running

	fittsParameters: {				// Current parameters from test (start-parameters)
		numOfTargets: importNumOfTargets[0],
		sequenceRadius: importSequenceRadius[0],
		targetRadius: importTargetRadius[0],
		randomize: true},

	active: false,					// Boolean - when tester is active testing
	data: [],						// Data to draw

	generateTarget: function() {
		this.target = this.arrayTargets[this.currentPosition];
		this.currentPosition = (this.currentPosition + Math.ceil(this.arrayTargets.length/2)) % this.arrayTargets.length;
		var target = testAreaSVG.selectAll('#target').data([this.target]);
		var insert = function(d) {
			   d.attr('cx', function(d) { return d.x; })
				.attr('cy', function(d) { return d.y; })
				.attr('r', function(d) { return d.w / 2; });
		}
		target.enter().append('circle').attr('id', 'target').style('fill', 'green').call(insert);
		target.transition().call(insert);
		this.active = true;
	},

	/*
	 * Run a new sequence
	 */
	runNewSequence: function() {
		this.currentTarget = 0;
		this.generateArrayTargets(
			this.fittsParameters.numOfTargets,
			this.fittsParameters.sequenceRadius,
			this.fittsParameters.targetRadius);
		var circles = testAreaSVG.selectAll('circle').data(this.arrayTargets);
		var insert = function(d) {
			   d.attr('cx', function(d) { return d.x; })
				.attr('cy', function(d) { return d.y; })
				.attr('r', function(d) { return d.w / 2; });
		}
		circles.enter().append('circle').attr('class', 'iso').call(insert);
		circles.transition().call(insert);
		circles.exit().transition().attr('r', 0).remove();
		this.currentPosition = 0;
		this.generateTarget();
		this.active = false;
		// Generate and display message
		var message = 'Sequence ' + (this.currentSequence + 1) + '/' + importNumOfSequences + ' : Press the circle to start test!'
		/* Comment terug weghalen!
		d3.select('body').append('div')
			.attr('class', 'msg')
			.text(message)
			.style('color', 'green')
			.style('opacity', 1)
			.transition()
			.duration(15000)
			.style('opacity', 0)
			.remove();
		*/
	},

	/*
	 * Generates an array of targets
	 * @param numOfTargets	: number of targets
	 * @param sequenceRadius: radius of the target-sequence
	 * @param targetRadius	: radius of an individual target
	 */
	generateArrayTargets: function(numOfTargets, sequenceRadius, targetRadius) {
		this.arrayTargets = [];							// Reset array of targets
		for (var i = 0; i < numOfTargets; i++) {		// Generate new array of targets
			this.arrayTargets[i] = {x: testDimension.center_x + ((sequenceRadius/2) * Math.cos((2 * Math.PI * i) / numOfTargets)),
				y: testDimension.center_y + ((sequenceRadius/2) * Math.sin((2 * Math.PI * i) / numOfTargets)),
				w: targetRadius};
		}
	},

	/*
	 * Remove targets
	 */
	removeTarget: function() {
		testAreaSVG.selectAll('#target').data([]).exit().remove();
		this.active = false;
	},

	/*
	 * Action when mouse is clicked
	 * @param x	: current mouse X position
	 * @param y : current mouse Y position
	 */
	mouseClicked: function(x, y) {
		if (distance({x: x, y: y}, this.target) < (this.target.w / 2)) {		// If click is on target
			this.removeTarget();
			if (this.currentTarget == 0)	{									// First target clicked
				this.startTime = new Date().getTime();
			} else if (this.currentTarget == this.arrayTargets.length)	{		// Last target clicked
				this.stopTime = new Date().getTime();
			} else {															// Other target clicked
				// Do nothing
			}
			if (this.currentTarget >= this.arrayTargets.length)	{				// End of previous sequence, start new sequence
				this.saveData();												// Save data last sequence
				this.setParameters();											// Set parameters new sequence
				this.currentTarget = 0;											// Reset counters
				this.currentPosition = 0;
				this.numOfErrors = 0;
				this.runNewSequence();											// Start new sequence
			} else {
				this.currentTarget++;											// Generate next target
				this.generateTarget();
			}
			this.lastMousePoint = {x: x, y: y, t: (new Date).getTime()};
		} else {																// If click is not on target (miss)
			this.numOfErrors++;
		}
	},

	/*
	 * Draws following lines on frame
	 * @param x	: current mouse X position
	 * @param y : current mouse Y position
	 */
	mouseMoved: function(x, y) {
		if (this.active) {														// If sequence-test active
			if (x == this.lastMousePoint.x && y == this.lastMousePoint.y) {		// If mouse not moved, should never happen...
				return;
			}
			var newMousePoint = {x: x, y: y, t: (new Date).getTime()}
			testAreaSVG.append('line')											// Draw following-lines
				.attr('x1', this.lastMousePoint.x)
				.attr('x2', newMousePoint.x)
				.attr('y1', this.lastMousePoint.y)
				.attr('y2', newMousePoint.y)
				.style('stroke', 'black')
				.transition()
				.duration(5000)
				.style('stroke-opacity', 0)
				.remove();
			this.lastMousePoint = newMousePoint;
		}
	},

	/*
	 * Set parameters for new sequence
	 */
	setParameters: function()	{
		if(this.currentSequence >= (importNumOfSequences - 1))	{
			this.currentSequence = 0;
			// ----- PRINT OUTPUT DATA ----- (only used in testing)
			var message = '';
			for (var i = 0; i < importNumOfSequences; i++)	{
				message = message + 'T=' + outputThroughput[i] + ' M=' + outputMeanTime[i] + ' E=' + outputNumOfErrors[i] + ' ';
			}
			d3.select('body').append('div')
				.attr('class', 'msg')
				.text(message)
				.style('color', 'red')
				.style('opacity', 1)
				.transition()
				.duration(20000)
				.style('opacity', 0)
				.remove();
			// -----------------------------
			// LOOP, At this point --> END PROGRAM
		} else {
			this.currentSequence = this.currentSequence + 1;
		}
		this.fittsParameters.numOfTargets = importNumOfTargets[this.currentSequence];
		this.fittsParameters.sequenceRadius = importSequenceRadius[this.currentSequence];
		this.fittsParameters.targetRadius = importTargetRadius[this.currentSequence];
		this.runNewSequence();
	},

	/*
	 * Save data at end of sequence
	 */
	saveData: function()	{
		// Number of error's
		outputNumOfErrors[this.currentSequence] = this.numOfErrors;
		// Mean Movement Time [seconds]
		var totalTime = this.stopTime - this.startTime;
		var MT = totalTime/this.fittsParameters.numOfTargets/1000;
		outputMeanTime[this.currentSequence] = MT;
		// Throughput [bits/second]
		var Ae = 2; 													// Nog aanpassen
		var We = 2; 													// Nog aanpassen
		var IDe = getIndexOfDifficulty(Ae, We);
		var TP = IDe/MT;
		outputThroughput[this.currentSequence] = TP;
	}
};

/*
 * Set dimensions from window
 * @param width		: outer width of the frame (in pixels)
 * @param height	: outer height of the frame (in pixels)
 * @param top		: top margin from the top edge (in pixels)
 * @param right		: right margin from the right edge (in pixels)
 * @param bottom	: bottom margin from the bottom edge (in pixels)
 * @param left		: left margin from the left edge (in pixels)
 */
function makeDimension(width, height, top, right, bottom, left) {
	return {width: width,
		height: height,
		innerWidth: width - (left + right),
		innerHeight: height - (top + bottom),
		top: top,
		right: right,
		bottom: bottom,
		left: left,
		center_x: (width - (left + right)) / 2 + left,
		center_y: (height - (top + bottom)) / 2 + top};
}

/*
 * If mouse is moved
 */
function mouseMoved()	{
	var locXY = d3.svg.mouse(this);
	fittsTest.mouseMoved(locXY[0], locXY[1])
}

/*
 * If mouse is clicked
 */
function mouseClicked()	{
	var locXY = d3.svg.mouse(this);
	fittsTest.mouseClicked(locXY[0], locXY[1]);
}

/*
 * Distance between two points
 * @param a	: point A (point with x and y coordinates)
 * @param b	: point B (point with x and y coordinates)
 */
function distance(a, b) {
	var dx = a.x - b.x;
	var dy = a.y - b.y;
	return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
}

/*
 * Get index of difficulty (Shannon formula)
 */
function getIndexOfDifficulty(Ae, We) {
	return Math.log((Ae / We) + 1) / Math.log(2);
}

/*
 * Test frame
 */
function bgRect(d, dim) {
	return d.append('rect')
		.attr('cx', 0)
		.attr('cy', 0)
		.attr('width', dim.width)
		.attr('height', dim.height)
		.attr('class', 'back');
}

/*
 * Describes the test-area for the HTML-page
 */
var testAreaSVG = d3.select('#test-area').append('svg')
	.attr('width', testDimension.width)
	.attr('height', testDimension.height)
	.style('pointer-events', 'all')
	.on('mousemove', mouseMoved)
	.on('mousedown', mouseClicked)
	.call(bgRect, testDimension);

// INITIALIZE CODE
fittsTest.active = false;
fittsTest.runNewSequence();
