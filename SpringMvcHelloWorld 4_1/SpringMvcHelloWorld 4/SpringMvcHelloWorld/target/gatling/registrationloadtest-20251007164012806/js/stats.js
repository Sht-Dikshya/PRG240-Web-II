var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "1000",
        "ok": "210",
        "ko": "790"
    },
    "minResponseTime": {
        "total": "383",
        "ok": "2511",
        "ko": "383"
    },
    "maxResponseTime": {
        "total": "4643",
        "ok": "4643",
        "ko": "3757"
    },
    "meanResponseTime": {
        "total": "2721",
        "ok": "4114",
        "ko": "2350"
    },
    "standardDeviation": {
        "total": "1111",
        "ok": "552",
        "ko": "910"
    },
    "percentiles1": {
        "total": "2881",
        "ok": "4454",
        "ko": "2250"
    },
    "percentiles2": {
        "total": "3533",
        "ok": "4571",
        "ko": "3168"
    },
    "percentiles3": {
        "total": "4579",
        "ok": "4623",
        "ko": "3670"
    },
    "percentiles4": {
        "total": "4623",
        "ok": "4634",
        "ko": "3740"
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
    "count": 210,
    "percentage": 21
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 790,
    "percentage": 79
},
    "meanNumberOfRequestsPerSecond": {
        "total": "200",
        "ok": "42",
        "ko": "158"
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
        "total": "1000",
        "ok": "210",
        "ko": "790"
    },
    "minResponseTime": {
        "total": "383",
        "ok": "2511",
        "ko": "383"
    },
    "maxResponseTime": {
        "total": "4643",
        "ok": "4643",
        "ko": "3757"
    },
    "meanResponseTime": {
        "total": "2721",
        "ok": "4114",
        "ko": "2350"
    },
    "standardDeviation": {
        "total": "1111",
        "ok": "552",
        "ko": "910"
    },
    "percentiles1": {
        "total": "2881",
        "ok": "4454",
        "ko": "2250"
    },
    "percentiles2": {
        "total": "3533",
        "ok": "4571",
        "ko": "3168"
    },
    "percentiles3": {
        "total": "4579",
        "ok": "4623",
        "ko": "3670"
    },
    "percentiles4": {
        "total": "4623",
        "ok": "4634",
        "ko": "3740"
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
    "count": 210,
    "percentage": 21
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 790,
    "percentage": 79
},
    "meanNumberOfRequestsPerSecond": {
        "total": "200",
        "ok": "42",
        "ko": "158"
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
