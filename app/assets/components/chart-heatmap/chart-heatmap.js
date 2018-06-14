/*
 * Copyright 2015 Andrew Gibson
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/**
 * Created by Andrew Gibson on 6/01/15.
 */

//The directive
function chartHeatmapDirective () {

    function link(scope, element, attrs) {
        //console.log("Link Function Called with chartData: ",scope.chartdata);

        scope.$watch('chartdata', function (chartdata) {
            console.log("Watch has been called with chartdata: ",chartdata);
            if (chartdata) {
                element.append(heatmapBuilder(chartdata,element));
            }
        });
    }

    return {
        restrict: "E",
        replace: true,
        template: '<div></div>',
        scope: {
            chartdata: '@chartdata'
            //term: '@term'
        },
        link: link
    }
}

//The  chart builder
function heatmapBuilder(chartdata,element) {

    var title = "Assessment type heatmap";
    var svg_hm = null;

    var data = JSON.parse(chartdata);

    //console.log("data",data);
    var types = d3.set(data.map(function(d) { return d.d;})).values();
    //console.log("types",types);
    var topics = d3.set(data.map(function(d) { return d.t;})).values();
    //console.log("topics",topics);

    var width = element.parent()[0].offsetWidth;

    var height = 1.4 * width;
    var pad = width*0.08;
    var margin = {top: pad, right: pad, bottom: pad, left: pad};
    var w = width - margin.left - margin.right;
    var h = height - margin.top - margin.bottom;

    svg_hm = d3.select(element[0])
        .append("svg:svg")
        .attr("width", w + margin.left + margin.right)
        .attr("height", h + margin.top + margin.bottom);

    var x = d3.scale.ordinal()
        .domain(topics)
        .rangeBands([margin.left, w]);

    var y = d3.scale.ordinal()
        .domain(types)
        .rangeBands([h,margin.top]);

    var colorLow = '#fffff9', colorHigh = '#770000';

    var colorScale = d3.scale.linear()
        .domain([0, 0.7])
        .range([colorLow, colorHigh]);

    var cellwidth = (w/(topics.length+1));
    var cellheight = (h/(types.length+1));

    var cell = svg_hm.selectAll("rect")
        .data(data)
        .enter().append("rect")
        .attr("x",function(d) { return x(d.t); })
        .attr("y",function(d) { return y(d.d); })
        .attr("width", 0.9*cellwidth)
        .attr("height", 0.9*cellheight)
        .style("fill", function(d) { console.log("f: ", d.v); return colorScale(d.v); });

    // Create the axes
    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .tickSize(10,0);

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")
        .tickSize(10,0);


    svg_hm.append("svg:g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + h + ")")
        .call(xAxis)
        .selectAll("text")
        .attr("transform", "rotate(-90)")
        .style("text-anchor", "end")
        .attr("x",-20)
        .attr("dy",(x.rangeBand()*-0.4));


        //;

    svg_hm.append("svg:g")
        .attr("class", "y axis")
        .attr("transform", "translate(" + margin.left + ",0)")
        .call(yAxis);

    // Add a title
     svg_hm.append("text")
        .attr("class","title")
        .attr("x", w/5)
        .attr("y", margin.top-10)
        .attr("text-anchor","middle")
        .text(title);

}

// Add directive to the app
angular
    .module(mainAppName)
    .directive("chartHeatmap",chartHeatmapDirective);
