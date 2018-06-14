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

// Code that doesn't need data
var WIDTH = 400, HEIGHT = 300;

var margin = {top: 20, right: 20, bottom: 20, left: 80 };
var W = WIDTH - margin.left - margin.right;
var H = HEIGHT - margin.top - margin.bottom;

var svg_bar = d3.select("#chart").append("svg")
                .attr("width", WIDTH)
                .attr("height", HEIGHT);


//This function is called when the data is loaded
var dataReady = function(error, data) {
    console.log("Data: "+JSON.stringify(data));
    console.log("Max: "+ d3.max(data, function(d) { return d.sales; }) );
    
    // Set the ranges (including the margins) - the domain is set once we have the data
    var x = d3.time.scale()
                .domain([new Date(2014,0,1), new Date(2014,11,31)])
                .range([margin.left, W]);
    var y = d3.scale.linear()
                .domain([0,d3.max(data, function(d) { return d.sales; })])
                .range([H, margin.top]);
    
    //The rects for bars - note that these are drawn from the top to the bottom

    svg_bar.selectAll("rect")
        .data(data)
        .enter()
        .append("rect")
        .attr("x",function(d,i) {return x(new Date(2014,i,1));})
        //.attr("y", function(d) {return y(d.sales);})
        .attr("y",H)
        .attr("width", 10)
        .attr("height",0)
        .transition()
        .delay(function(d, i) { return i * 250 })
        .duration(500)
        .attr("y", function(d) {return y(d.sales);}) //the top of the bar
        .attr("height", function(d) {return H - y(d.sales);}); //the length of the bar
        //.style("fill","navy");

    //Circle to mark the actual data points
    /*
    svg_bar.selectAll("circle")
        .data(data)
        .enter()
        .append("circle")
        .attr("cx", function(d,i) {return x(new Date(2014,i,1));})
        .attr("cy", function(d) {return y(d.sales);})
        .attr("r", 4)
        .style("fill","orange"); */
    
    // Create the axes
    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .ticks(d3.time.months)
        .tickSize(16,0)
        .tickFormat(d3.time.format("%b"));
    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")
        .ticks(7)
        .tickSize(16,0);
    
    // Add the axes
    svg_bar.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(5," + H + ")")
        .call(xAxis);
    svg_bar.append("g")
        .attr("class", "y axis")
        .attr("transform", "translate(" + margin.left + ",0)")
        .call(yAxis);
    
    // Add a title
    svg_bar.append("text")
        .attr("class","chartTitle")
        .attr("x", W/2)
        .attr("y", margin.top)
        .attr("text-anchor","middle")
        .text("Sales by Month");
}

//Load the data - the anon function ensure the type for sales is a number
d3.csv("data/myData.csv", function(d) { return {month: d.month, sales: +d.sales};}, dataReady);
