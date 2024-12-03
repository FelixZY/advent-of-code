import { readFileSync } from "fs";

function main() {
  console.log("Part 1");
  const memory = readFileSync(__dirname + "/input.txt").toString("utf-8");

  let sum = Array.from(memory.matchAll(/mul\((\d{1,3}),(\d{1,3})\)/g)).reduce(
    (sum, match) => sum + parseInt(match[1]) * parseInt(match[2]),
    0
  );
  console.log(sum);
}

main();
