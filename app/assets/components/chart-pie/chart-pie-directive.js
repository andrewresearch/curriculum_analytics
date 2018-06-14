/**
 * Created by andrew on 6/01/15.
 */

//The directive
function chartPieDirective () { 
    
    function link(scope, element, attrs) {
            //console.log("Link Function Called with chartData: ",scope.chartdata);
            scope.$watch('chartdata', function (chartdata) {
                //console.log("Watch has been called: ",chartdata);
                if (chartdata) {
                   element.append(pieChartBuilder(chartdata,element));
                }
            })
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

//The pie chart builder
function pieChartBuilder(chartdata,element) {
    
    var svg = null;
    //console.log("chartdata: ",chartdata);
    var data = JSON.parse(chartdata);
    //console.log("data: ",data);

    var width = element.parent()[0].offsetWidth;
    //width = 300
    var height = width/1.5;
    var pad = width*0.2;
    var margin = {top: pad, right: pad, bottom: pad, left: pad};
    var w = width - margin.left - margin.right;
    var h = height - margin.top - margin.bottom;
    var r = w/2; //Math.min(w, h) / 2; //outer radius
    var ir = r *0.5;
    var labelr = r -20; // radius for label anchor
    var color = d3.scale.category10();
    var donut = d3.layout.pie().value(function(d) { return d.count; });
    var arc = d3.svg.arc().innerRadius(ir).outerRadius(r);

    svg = d3.select(element[0])
        .data([data])
        .append("svg:svg")
        .attr("width", w + margin.left + margin.right)
        .attr("height", h + margin.top + margin.bottom);

    var arcs = svg.selectAll("g.arc")
        .data(donut)
        .enter().append("svg:g")
        .attr("class", "arc")
        .attr("transform", "translate(" + (r + 30) + "," + r + ")");

    arcs.append("svg:path")
        .attr("fill", function(d, i) { return color(i); })
        .attr("d", arc);

    var legend = svg.selectAll(".legend")
        .data(data)
        .enter().append("g")
        .attr("class", "legend")
        .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

    legendx = width - (18+(pad/2));
    legend.append("rect")
        .attr("x", legendx)
        .attr("width", 18)
        .attr("height", 18)
        .style("fill", function(d, i) { return color(i); });

    legend.append("text")
        .attr("x", legendx - 10)
        .attr("y", 9)
        .attr("dy", ".35em")
        .style("text-anchor", "end")
        .text(function(d) {return d.word; });


    // Arc Labels
    var totalCount = d3.sum(data, function(d) { return d.count;});
    var    toPercent = d3.format("0.1%");

    arcs.append("svg:text") //add a label to each slice
        .attr("transform", function(d) {
            d.innerRadius = 0;
            d.outerRadius = r;
            return "translate(" + arc.centroid(d) + ")";
        })
        .attr("text-anchor", "middle")
        .style("font", "12px arial")
        .text(function(d, i) { return (toPercent(data[i].count / totalCount)); });

}

// Add directive to the app
angular 
    .module(mainAppName)
    .directive("chartPie",chartPieDirective);
