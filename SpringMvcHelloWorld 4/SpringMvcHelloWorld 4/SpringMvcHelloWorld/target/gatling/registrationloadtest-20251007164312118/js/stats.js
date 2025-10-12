var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "300",
        "ok": "146",
        "ko": "154"
    },
    "minResponseTime": {
        "total": "194",
        "ok": "1428",
        "ko": "194"
    },
    "maxResponseTime": {
        "total": "2480",
        "ok": "2480",
        "ko": "820"
    },
    "meanResponseTime": {
        "total": "1361",
        "ok": "2314",
        "ko": "458"
    },
    "standardDeviation": {
        "total": "945",
        "ok": "219",
        "ko": "137"
    },
    "percentiles1": {
        "total": "792",
        "ok": "2403",
        "ko": "444"
    },
    "percentiles2": {
        "total": "2401",
        "ok": "2441",
        "ko": "530"
    },
    "percentiles3": {
        "total": "2460",
        "ok": "2467",
        "ko": "720"
    },
    "percentiles4": {
        "total": "2470",
        "ok": "2472",
        "ko": "811"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 146,
    "percentage": 49
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 154,
    "percentage": 51
},
    "meanNumberOfRequestsPerSecond": {
        "total": "100",
        "ok": "48.667",
        "ko": "51.333"
    }
},
contents: {
"req_register-user--650051800": {
        type: "REQUEST",
        name: "Register User",
path: "Register User",
pathFormatted: "req_register-user--650051800",
stats: {
    "name": "Register User",
    "numberOfRequests": {
        "total": "300",
        "ok": "146",
        "ko": "154"
    },
    "minResponseTime": {
        "total": "194",
        "ok": "1428",
        "ko": "194"
    },
    "maxResponseTime": {
        "total": "2480",
        "ok": "2480",
        "ko": "820"
    },
    "meanResponseTime": {
        "total": "1361",
        "ok": "2314",
        "ko": "458"
    },
    "standardDeviation": {
        "total": "945",
        "ok": "219",
        "ko": "137"
    },
    "percentiles1": {
        "total": "792",
        "ok": "2403",
        "ko": "444"
    },
    "percentiles2": {
        "total": "2401",
        "ok": "2441",
        "ko": "530"
    },
    "percentiles3": {
        "total": "2460",
        "ok": "2467",
        "ko": "720"
    },
    "percentiles4": {
        "total": "2470",
        "ok": "2472",
        "ko": "811"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 146,
    "percentage": 49
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 154,
    "percentage": 51
},
    "meanNumberOfRequestsPerSecond": {
        "total": "100",
        "ok": "48.667",
        "ko": "51.333"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
