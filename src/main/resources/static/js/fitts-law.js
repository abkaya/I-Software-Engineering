/* AUTHOR		: Jan Huijghebaert
 * DESCRIPTION	: Fitts Test Javascript
 */

// "use strict";

/* MESSAGE WEERGEVEN
d3.select('body').append('div')
	.attr('class', 'msg')
	.text('updating plots...')
	.style('opacity', 1)
	.transition()
	.duration(2000)
	.style('opacity', 0)
	.remove();
*/
// ---------------------------------------------
// INPUT
var importNumOfTargets = 9;			// Steed oneven, grenzen nog te bepalen
var importSequenceRadius = 350;		// Grenzen nog bepalen
var importTargetRadius = 50;		// Grenzen nog bepalen
// OUTPUT

// ---------------------------------------------

var testDimension = makeDimension(1140, 650, 30, 30, 30, 30);				// Afmetingen canvas

var fittsTest = {
	target: {x: 0, y: 0, r: 10},
	start: {x: 0, y: 0, t: 0},
	last: {},

	isoPositions: [],
	currentPosition: 0,
	currentCount: 0,

	miss: 0,																		// Aantal error's
	isoLimits: {minD: 120, maxD: 560, minW:10 , maxW: 100},							// Plot limieten van test
	isoParams: {num: importNumOfTargets, distance: importSequenceRadius, width: importTargetRadius, randomize: true},		// Actuele parameters van test (start-waarden)

	currentPath: [],
	active: false,

	data: [],
	currentDataSet: 0,
	dataCnt: 0,

	colour: d3.scale.category10(),

	sumID: 0,
	sumTime: 0,

	generateTarget: function() {
		this.target = this.isoPositions[this.currentPosition];
		this.target.distance = this.isoParams.distance;
		this.currentPosition = (this.currentPosition + Math.ceil(this.isoPositions.length/2)) % this.isoPositions.length;
		var target = testAreaSVG.selectAll('#target').data([this.target]);
		var insert = function(d) {
			d.attr('cx', function(d) { return d.x; })
				.attr('cy', function(d) { return d.y; })
				.attr('r', function(d) { return d.w / 2; });
		}
		target.enter()
			.append('circle')
			.attr('id', 'target')
			.style('fill', 'green')
			.call(insert);
		target.transition()
			.call(insert);
		this.active = true;
	},

	updateISOCircles: function() {
		this.currentCount = 0;
		this.generateISOPositions(this.isoParams.num,
			this.isoParams.distance,
			this.isoParams.width);
		var circles = testAreaSVG.selectAll('circle').data(this.isoPositions);
		var insert = function(d) {
			d.attr('cx', function(d) { return d.x; })
				.attr('cy', function(d) { return d.y; })
				.attr('r', function(d) { return d.w / 2; });
		}
		circles.enter()
			.append('circle')
			.attr('class', 'iso')
			.call(insert);
		circles.transition()
			.call(insert);
		circles.exit()
			.transition()
			.attr('r', 0)
			.remove();
		this.currentPosition = 0;
		this.generateTarget();
		this.active = false;
	},

	generateISOPositions: function(num, d, w) {
		// remove all data from live view
		this.isoPositions = [];
		for (var i = 0; i < num; i++) {
			this.isoPositions[i] = {x: testDimension.cx + ((d/2) * Math.cos((2 * Math.PI * i) / num)),
				y: testDimension.cy + ((d/2) * Math.sin((2 * Math.PI * i) / num)),
				w: w};
		}
	},

	removeTarget: function() {
		testAreaSVG.selectAll('#target').data([])
			.exit()
			.remove();
		this.active = false;
		this.currentPath = [];
	},

	mouseClicked: function(x, y) {
		if (distance({x: x, y: y}, this.target) < (this.target.w / 2)) {
			this.removeTarget();
			if (this.isoParams.randomize && this.currentCount >= this.isoPositions.length) {
				this.randomizeParams();
				this.currentCount = 0;
				this.currentPosition = 0;
				this.miss = 0;
				this.updateISOCircles;
				this.generateTarget();
				this.active = false;
			} else {
				this.currentCount++;
				this.generateTarget();
			}
			this.last = {x: x, y: y, t: (new Date).getTime()};
			this.start = this.last;
			this.currentPath.push(this.last);
		} else {
			this.miss++;
		}
	},

	mouseMoved: function(x, y) {
		if (this.active) {
			// skip if the mouse did actually not move
			// that should practically never happen...
			if (x == this.last.x && y == this.last.y) {
				return;
			}
			var newPoint = {x: x, y: y, t: (new Date).getTime()}
			this.currentPath.push(newPoint)
			var dt = newPoint.t - this.last.t;
			var dist = distance(this.last, {x: x, y: y})
			if (dt > 0)
				var speed = dist / dt;
			else
				var speed = 0;
			testAreaSVG.append('line')
				.attr('x1', this.last.x)
				.attr('x2', newPoint.x)
				.attr('y1', this.last.y)
				.attr('y2', newPoint.y)
				.style('stroke', 'black')
				.transition()
				.duration(5000)
				.style('stroke-opacity', 0)
				.remove();
			this.last = newPoint;
		}
	},

	randomizeParams: function() {
		this.isoParams.distance = Math.floor(randomAB(this.isoLimits.minD, this.isoLimits.maxD));
		this.isoParams.width = Math.floor(randomAB(this.isoLimits.minW, this.isoLimits.maxW));
		this.updateISOCircles();
	},

	setParameters: function()	{
		this.isoParams.distance = importSequenceRadius;
		this.isoParams.width = importTargetRadius;
		this.updateISOCircles();
	}
};

function makeDimension(width, height, top, right, bottom, left) {
	return {width: width,
		height: height,
		innerWidth: width - (left + right),
		innerHeight: height - (top + bottom),
		top: top,
		right: right,
		bottom: bottom,
		left: left,
		cx: (width - (left + right)) / 2 + left,
		cy: (height - (top + bottom)) / 2 + top};
}

function randomAB(a, b) {
	return a + Math.random() * (b - a);
}

function mouseMoved()	{
	var m = d3.svg.mouse(this);
	fittsTest.mouseMoved(m[0], m[1])
}

function mouseClicked()	{
	var m = d3.svg.mouse(this);
	fittsTest.mouseClicked(m[0], m[1]);
}

function distance(a, b) {
	var dx = a.x - b.x;
	var dy = a.y - b.y;
	return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
}

function clampInt(lower, upper, x) {
	return Math.min(upper, Math.max(lower, Math.floor(x)));
}

function bgRect(d, dim) {
	return d.append('rect')
		.attr('cx', 0)
		.attr('cy', 0)
		.attr('width', dim.width)
		.attr('height', dim.height)
		.attr('class', 'back');
}

// Test AREA in HTML
var testAreaSVG = d3.select('#test-area').append('svg')
	.attr('width', testDimension.width)
	.attr('height', testDimension.height)
	.style('pointer-events', 'all')
	.on('mousemove', mouseMoved)
	.on('mousedown', mouseClicked)
	.call(bgRect, testDimension);

// Init code
fittsTest.active = false;
fittsTest.generateISOPositions(15, 150, 10);
fittsTest.updateISOCircles();