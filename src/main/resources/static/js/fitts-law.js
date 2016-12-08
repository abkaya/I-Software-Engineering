// "use strict";

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

var testDimension = makeDimension(1140, 650, 30, 30, 30, 30);				// Afmetingen canvas
var plotPositionDimension = makeDimension(220, 200, 30, 30, 30, 30);
var plotHitsDimension = plotPositionDimension;
var scatterEffectiveDimension = makeDimension(540, 300, 30, 30, 30, 50);
var positionEffectiveDimension = makeDimension(540, 200, 30, 30, 30, 40);
var histDimension = makeDimension(540, 300, 30, 30, 30, 50);

var MAX_TIME = 2000;
var UPDATE_DELAY = MAX_TIME;
var MAX_SPEED = 6; // pixel/ms

function v(v) {
	var colour = 'rgb(' + clampInt(0, 255, (v / MAX_SPEED) * 255) + ', 0, 0)';
	return colour;
};

var effScatterX = d3.scale.linear()
	.domain([0.5, 6.5])
	.range([0, scatterEffectiveDimension.innerWidth]);

var effScatterY = d3.scale.linear()
	.domain([MAX_TIME, 0])
	.range([0, scatterEffectiveDimension.innerHeight]);

var fittsTest = {
	target: {x: 0, y: 0, r: 10},
	start: {x: 0, y: 0, t: 0},
	last: {},

	isoPositions: [],
	currentPosition: 0,
	currentCount: 0,

	miss: 0,																		// Aantal error's
	isoLimits: {minD: 120, maxD: 560, minW:10 , maxW: 100},							// Plot limieten van test
	isoParams: {num: varNumImport, distance: 350, width: 50, randomize: true},		// Actuele parameters van test (start-waarden)

	currentPath: [],
	active: false,

	data: [],
	currentDataSet: 0,
	dataCnt: 0,

	colour: d3.scale.category10(),

	sumID: 0,
	sumTime: 0,

	updateTimeoutHandle: undefined,

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
			.style('fill', 'red')
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
			this.addDataPoint({start: this.start,
				target: this.target,
				path: this.currentPath,
				hit: {x: x, y: y, t: (new Date).getTime()}});
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
			// set timeout for updating plots
			if (this.updateTimeoutHandle) {
				window.clearTimeout(this.updateTimeoutHandle);
			}
			this.updateTimeoutHandle = window.setTimeout(this.updatePlots, UPDATE_DELAY, this);
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
				.style('stroke', v(speed))
				.transition()
				.duration(5000)
				.style('stroke-opacity', 0)
				.remove();
			this.last = newPoint;
		}
	},

	addDataPoint: function(data) {
		// add point to data array for plotting into ID/time scatter plot
		if (this.active == false)
			return;
		var dt = data.hit.t - data.start.t;
		if (dt < MAX_TIME) {   // skip if obvious outlier
			var dist = distance(data.target, data.start);
			var id = shannon(dist, data.target.w);
			this.data[this.currentDataSet].data.push({time: dt, distance: data.target.distance, width: data.target.w, hit: data.hit,
				start: data.start, target: data.target, path: data.path});
			var A = data.start;
			var B = data.target;
			var path = data.path;
			var hit = {}
			var q = project(A, B, data.hit);
			hit.x = distance(q, B) * sign(q.t - 1);
			hit.y = distance(q, data.hit) * isLeft(A, B, data.hit);
			var last = { x: 0, y: 0, t: data.start.t, v: 0};
			for (var i = 0; i < path.length; i++) {
				var p = path[i];
				var q = project(A, B, p);
				var x = distance(q, A) * sign(q.t);
				var y = distance(q, p) * isLeft(A, B, p);
				var dt = p.t - last.t;
				var dist = distance(last, {x: x, y: y});
				if (dt > 0)
					var speed = dist / dt;
				else
					var speed = 0;

				var last = {}
				last.x = x;
				last.y = y;
				last.t = p.t;
				last.v = speed;
			}
		}
	},

	randomizeParams: function() {
		this.isoParams.distance = Math.floor(randomAB(this.isoLimits.minD, this.isoLimits.maxD));
		this.isoParams.width = Math.floor(randomAB(this.isoLimits.minW, this.isoLimits.maxW));
		this.updateISOCircles();
	},

	addDataSet: function() {
		this.updatePlots(this);
		this.dataCnt++;
		var num = this.dataCnt;
		var colour = this.colour(randomAB(0, 10));
		this.data[num] = {data: [], colour: colour};
		this.currentDataSet = num
		var div = d3.select('#dataSets').append('div')
			.attr('id', 'dataSet' + num)
			.text('Data Set ' + num + ' ')
			.style('background-color', colour);
		var buttonID ='removeDataSet' + num;
		div.append('button')
			.attr('id', buttonID)
			.attr('type', 'button')
			.text('delete!');
		var that = this;
		$('#' + buttonID).click(function() {
			that.deleteDataSet(num);
			fittsTest.active = false;
		});
		$('#dataSet' + num).click(function() {
			if (assIsKey(num, that.data)) {
				that.currentDataSet = num;
				that.highlightDataSet(num);
			}
			fittsTest.active = false;
		})
		this.highlightDataSet(num);
	},

	deleteDataSet: function(num) {
		if (assSize(this.data) == 1) {
			alert('Cannot delete data set! Create another data set first.')
		} else {
			d3.select('#dataSet' + num).remove();
			delete this.data[num];
			if (num == this.currentDataSet) {
				var first = parseInt(assFirstKey(this.data));
				this.currentDataSet = first;
				this.highlightDataSet(first);
			}
			this.updatePlots(this);
		}
	},

	highlightDataSet: function(num) {
		d3.selectAll('#dataSets div')
			.attr('class', '');
		d3.select('#dataSet' + num)
			.attr('class', 'active')
	},

	updatePlots: function(that) {
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		d3.select('body').append('div')
			.attr('class', 'msg')
			.text('updating plots...')
			.style('opacity', 1)
			.transition()
			.duration(2000)
			.style('opacity', 0)
			.remove();

		// we haven't moven inside the test area, so we can as well disable the test for now
		that.active = false;

		// for each data set compute We and IDe and Throughput for each category

		// process data
		var dataSetIndex = -1; // evil hack to make it start at 0 then.
		for (var key in that.data) { // for each data set
			dataSetIndex++;
			var groups = [];
			for (var i = 0; i < that.data[key].data.length; i++) { // for each datum
				var datum = that.data[key].data[i];
				var groupID = datum.distance.toString() + datum.width.toString();
				if (!groups[groupID]) {
					groups[groupID] = [];
				}
				var q = project(datum.start, datum.target, datum.hit);
				var y = distance(q, datum.hit) * isLeft(datum.start, datum.target, datum.hit);
				datum.realDistance = distance(datum.start, datum.hit); // use real distance here.
				datum.projectedHitOffsetX = distance(q, datum.target) * sign(q.t - 1);
				datum.projectedHitOffsetY = y;
				groups[groupID].push(datum);
			}

			var newData = [];
			for (var group in groups) {
				if (groups[group].length < 3) { // exlcude groups with length < 3
					continue;
				}
				var xEffective = 4.133 * Math.sqrt(variance(groups[group], function(d) { return d.projectedHitOffsetX; }))
				var yEffective = 4.133 * Math.sqrt(variance(groups[group], function(d) { return d.projectedHitOffsetY; }))
				var dEffective = mean(groups[group], function(d) { return d.realDistance; });
				for (var i = 0; i < groups[group].length; i++) {
					var datum = groups[group][i];
					var We = Math.min(xEffective, yEffective);
					var De = dEffective;
					datum.IDe = shannon(De, We);
					datum.throughput = 1000 * (datum.IDe/datum.time);
					newData.push(datum);
				}
			}
			var colour = that.data[key].colour;
			var insert = function(d) {
				d.attr('cx', function(d) { return effScatterX(d.IDe); })
					.attr('cy', function(d) { return effScatterY(d.time); })
					.attr('r', 5);
			}

			// ==================== regression ========================
			var covTIDe = cov(newData,
				function(d) { return d.time; },
				function(d) { return d.IDe});

			var varIDe = variance(newData, function(d) { return d.IDe; })

			if (varIDe > 0)
				var b = covTIDe / varIDe;
			else
				var b = 0;

			var mT = mean(newData, function(d) { return d.time; });
			var mIDe = mean(newData, function(d) { return d.IDe; });
			var a = mT - b * mIDe;

			// ============== histogram ====================
			var histThroughput = d3.layout.histogram()
				.bins(20)
				.range([0,10])
				.value(function(d){return d.throughput;})

			var throughputHistogramData = histThroughput(newData)

			var histX = d3.scale.ordinal()
				.domain(throughputHistogramData.map(function(d) { return d.x; }))
				.rangeRoundBands([0, histDimension.innerWidth]);

			var numDataSets = assSize(that.data);

			// ==================== eff position and speed ===================
			for (var i = 0; i < newData.length; i++)
			{
				var last = { x: 0, y: 0, t: newData[i].start.t, v: 0};
				var A = newData[i].start;
				var B = newData[i].target
				var dAB = distance(A, B);
				var offset = newData[i].distance - dAB;
				offset = 0;

				for (var j = 0; j < newData[i].path.length; j++) {

					var p = newData[i].path[j];

					var q = project(A, B, p);
					var x = distance(q, A) * sign(q.t);
					var y = distance(q, p) * isLeft(A, B, p);

					var dt = p.t - last.t;
					var dist = distance(last, {x: x, y: y});
					if (dt > 0)
						var speed = dist / dt;
					else
						var speed = 0;

					var last = {}
					last.x = x;
					last.y = y;
					last.t = p.t;
					last.v = speed;
				}
			}
		}
	}
};

function cov(data, extractorA, extractorB) {
	if (data.length <= 1) { // no covariance for 0 or 1 element.
		return 0;
	}
	var mA = mean(data, extractorA);
	var mB = mean(data, extractorB);
	var cov = 0;
	for (var i = 0; i < data.length; i++) {
		cov += (extractorA(data[i]) - mA) * (extractorB(data[i]) - mB);
	}
	return cov / (data.length - 1);
}

function variance(data, extractor) {
	return cov(data, extractor, extractor);
}

function mean(data, extractor) {
	var sum = 0;
	for (var i = 0; i < data.length; i++) {
		sum += extractor(data[i]);
	}
	return sum / data.length;
}

function randomAB(a, b) {
	return a + Math.random() * (b - a);
}

function assSize(assArr) {
	var size = 0;
	for (var _ in assArr) {
		size++;
	}
	return size;
}

function assFirstKey(assArr) {
	for (var key in assArr) {
		return key;
		break;
	}
}

function assIsKey(needle, assArr) {
	for (var key in assArr) {
		if (needle == key) {
			return true;
		}
	}
	return false;
}

// Project a point q onto the line p0-p1
function project(A, B, p) {
	var AB = minus(B, A);
	var AB_squared = dot(AB, AB);
	if (AB_squared == 0) {
		return A;
	} else {
		var Ap = minus(p, A);
		var t = dot(Ap, AB) / AB_squared;
		return {x: A.x + t * AB.x,
			y: A.y + t * AB.y,
			t: t};
	}
}

function mouseMoved()	{
	var m = d3.svg.mouse(this);
	fittsTest.mouseMoved(m[0], m[1])
}

function mouseClicked()	{
	var m = d3.svg.mouse(this);
	fittsTest.mouseClicked(m[0], m[1]);
}

function dot(a, b) {
	return (a.x * b.x) + (a.y * b.y);
}

function isLeft(A, B, p){ // determine-which-side-of-a-line-a-point-lies
	return ((B.x - A.x)*(p.y - A.y) - (B.y - A.y)*(p.x - A.x)) >= 0 ? 1: -1;
}

function minus(a, b) {
	return {x: a.x - b.x, y: a.y - b.y};
}

function distance(a, b) {
	var dx = a.x - b.x;
	var dy = a.y - b.y;
	return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
}

function sign(a) {
	return a >=0 ? 1 : -1;
}

function clampInt(lower, upper, x) {
	return Math.min(upper, Math.max(lower, Math.floor(x)));
}

function shannon(A, W) {
	return Math.log(A / W + 1) / Math.log(2);
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
fittsTest.addDataSet();