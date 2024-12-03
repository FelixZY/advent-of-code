import { readFileSync } from "fs";

function main() {
  console.log("Part 1");
  console.log(part1());
}

export function part1(): number {
  const memory = readFileSync(__dirname + "/input.txt").toString("utf-8");

  return Array.from(memory.matchAll(/mul\((\d{1,3}),(\d{1,3})\)/g)).reduce(
    (sum, match) => sum + parseInt(match[1]) * parseInt(match[2]),
    0
  );
}

main();
