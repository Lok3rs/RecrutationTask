// This task solved also with Python and Java you can find on my github -> github.com/Lok3rs/RecrutationTask
// In order to run it you need to install fs and readline using npm and run it by node -> node main.js filename
import fs from "fs";

const maxElephantsNumber = 1000000;

let baseElephantOrder = new Array(maxElephantsNumber);
let perm = new Array(maxElephantsNumber).fill(null);
let checked = new Array(maxElephantsNumber).fill(false);

let minElephantWeight = Number.MAX_VALUE;

const main = () => {
    checkMinimumStrengthNeeded()
}

const checkMinimumStrengthNeeded = () => {
    fs.readFile(0, 'utf8', (err, data) => {
        const minStrengthNeeded = openFileCallback(err, data);
        console.log(minStrengthNeeded);
    });
}

const openFileCallback = (err, data) => {
    if (err) return "No file found";

    const dataLines = data.match(/[^\r\n]+/g);
    let numberOfElephants;
    let elephantsWeights;
    let baseOrder;
    let targetOrder;
    try {
        numberOfElephants = parseInt(dataLines[0]);
        elephantsWeights = transformStringIntoNumbersArray(dataLines[1]);
        baseOrder = transformStringIntoNumbersArray(dataLines[2]);
        targetOrder = transformStringIntoNumbersArray(dataLines[3]);
    } catch {
        return "Invalid data format provided"
    }

    for (let i = 0; i < numberOfElephants; i++) {
        minElephantWeight = Math.min(minElephantWeight, elephantsWeights[i]);
        baseElephantOrder[i] = baseOrder[i] - 1;
        perm[targetOrder[i] - 1] = baseElephantOrder[i];
    }

    let minStrengthNeeded = 0;

    for (let i = 0; i < numberOfElephants; i++) {
        if (!checked[i]) {
            let minCycleWeight = Number.MAX_VALUE;
            let cycleWeightsSum = 0;
            let cur = i;
            let cycleLength = 0;

            do {
                if (typeof cur == "undefined") return "Invalid data format provided";
                minCycleWeight = Math.min(minCycleWeight, elephantsWeights[cur]);
                cycleWeightsSum += elephantsWeights[cur];
                cur = perm[cur];
                checked[cur] = true;
                cycleLength++;
            } while (cur !== i)
            minStrengthNeeded += Math.min(
                cycleWeightsSum + (cycleLength - 2) * minCycleWeight,
                cycleWeightsSum + minCycleWeight + (cycleLength + 1) * minElephantWeight
            )
        }
    }
    return minStrengthNeeded;
}

const transformStringIntoNumbersArray = string => string.split(" ").map(el => parseInt(el));

main();
