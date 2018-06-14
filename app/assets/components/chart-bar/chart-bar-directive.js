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

//The directive function
function chartBarDirective () { 
    
    function link(scope, element, attrs) {

        scope.$watch('chartdata', function (chartdata) {
            if (chartdata) {
               element.append(barChartBuilder(chartdata,element));
            }
        });
    }
    
    return {
        restrict: "E",
        replace: true,
        template: '<div></div>',
        scope: {
            chartdata: '@chartdata'
        },
        link: link
    }
}

//The  chart builder
function barChartBuilder(chartdata,element) {
    
    var svg_bar = null;
    //console.log("chartdata: ",chartdata);
    var data = JSON.parse(chartdata);
    console.log("data: ",data);

    var width = element.parent()[0].offsetWidth;
    //var width = element.parent()[0].offsetWidth;
    console.log("width3: "+width);
    var height = width*2;
    var pad = width*0.1;
    var margin = {top: pad, right: pad, bottom: pad, left: pad};
    var w = width - margin.left - margin.right;
    var h = height - margin.top - margin.bottom;
    
    svg_bar = d3.select(element[0])
        .append("svg:svg")
        .attr("width", w + margin.left + margin.right)
        .attr("height", h + margin.top + margin.bottom);

    var x = d3.scale.linear()
                .domain([0,d3.max(data, function(d) { return d.y[1]})])
                .range([margin.left, w]);
    
    var y = d3.scale.ordinal()
                .domain(data.map(function(d) { return d.t; }))
                .rangeBands([margin.top,h]);
    
    //The rects for bars - note that these are drawn from the top to the bottom
    //var barthickness = 20;

    var year1 = svg_bar.append("g");
    var year2 = svg_bar.append("g");
    var year3 = svg_bar.append("g");

    var barheight = 3;
    var y2offset = (y.rangeBand()/2)-1;
    var y1offset = y2offset - 4;
    var y3offset = y2offset + 4;

    year1.selectAll("rect")
        .data(data)
        .enter()
        .append("rect")
        .attr("height",barheight)
        .attr("x", margin.left)
        .attr("y", function(d) {return (y(d.t)+y1offset);})
        .attr("width", function(d) {return x(d.y[0])-margin.left;})
        .style({'fill':'#99c'});

    year2.selectAll("rect")
        .data(data)
        .enter()
        .append("rect")
        .attr("height",barheight)
        .attr("x", margin.left)
        .attr("y", function(d) {return (y(d.t)+ y2offset);})
        .attr("width", function(d) {return x(d.y[1])-margin.left;})
        .style({'fill':'#d00'});

    year3.selectAll("rect")
        .data(data)
        .enter()
        .append("rect")
        .attr("height",barheight)
        .attr("x", margin.left)
        .attr("y", function(d) {return (y(d.t)+y3offset);})
        .attr("width", function(d) {return x(d.y[2])-margin.left;})
        .style({'fill':'#9c9'});

    svg_bar.selectAll("text")
        .data(data)
        .enter()
        .append("text")
        .attr("y", function(d) {return y(d.t)+y3offset;})
        .attr("x", function(d) {return x(d.y[0])+14;})
        .text(function(d){ return "("+d.y+")"; }).style({'fill':'#446','font-size':'10px'});
    
    //Circle to mark the actual data points
   /* var tickoffset = h/(data.length*2)
    svg_bar.selectAll("circle")
        .data(data)
        .enter()
        .append("circle")
        .attr("cx", function(d) {return x(d.y[1]);})
        .attr("cy", function(d) {return y(d.t);})
        .attr("r", 4)
        .style("fill","orange"); */
    
    // Create the axes
    
    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .ticks(10)
        .tickSize(16,0);
        //.tickFormat(d3.time.format("%b"));
    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")
        .tickSize(2);
        //.tickFormat(function(d){ return d.t; })
        //.tickValues(d3.range(data.length+1));
				
    
    // Add the axes
    svg_bar.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(5," + h + ")")
        .call(xAxis);
    svg_bar.append("g")
        .attr("class", "y axis")
        .attr("transform", "translate(" + margin.left + ",0)")
        .call(yAxis);
    
    // Add a title
    /*
    svg_bar.append("text")
        .attr("class","chartTitle")
        .attr("x", w/2)
        .attr("y", margin.top)
        .attr("text-anchor","middle")
        .text("Sales by Month"); 
        */

}

// Add directive to the app
angular 
    .module(mainAppName)
    .directive("chartBar",chartBarDirective);
