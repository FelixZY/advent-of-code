import { expect, test } from "bun:test";
import { part1, part2 } from ".";

test("part1", () => {
  expect(part1()).toEqual(159892596);
});

test("part2", () => {
  expect(part2()).toEqual(92626942);
});
