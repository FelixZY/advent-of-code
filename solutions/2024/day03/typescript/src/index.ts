import { readFileSync } from "fs";

function main() {
  console.log("Part 1");
  console.log(part1());
  console.log("Part 2");
  console.log(part2());
}

export function part1(): number {
  const memory = readFileSync(__dirname + "/input.txt").toString("utf-8");

  return Array.from(memory.matchAll(/mul\((\d{1,3}),(\d{1,3})\)/g)).reduce(
    (sum, match) => sum + parseInt(match[1]) * parseInt(match[2]),
    0
  );
}

export function part2(): number {
  const memory = readFileSync(__dirname + "/input.txt").toString("utf-8");
  let mulEnabled = true;

  return Array.from(
    memory.matchAll(/(mul)\((\d{1,3}),(\d{1,3})\)|(do)\(\)|(don't)\(\)/g)
  ).reduce((sum, match) => {
    if (match[1]) {
      return mulEnabled ? sum + parseInt(match[2]) * parseInt(match[3]) : sum;
    }
    if (match[4]) {
      mulEnabled = true;
      return sum;
    }
    if (match[5]) {
      mulEnabled = false;
      return sum;
    }
    throw new Error(`Not implemented: ${match}`);
  }, 0);
}

main();
