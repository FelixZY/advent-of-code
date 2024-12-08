from dataclasses import dataclass
from itertools import repeat, chain
from pathlib import Path

from more_itertools.more import distinct_permutations


@dataclass
class PlusOperator:
    symbol = "+"

    def apply(self, x, y):
        return x + y

    def __hash__(self):
        return hash(self.symbol)


@dataclass
class TimesOperator:
    symbol = "*"

    def apply(self, x, y):
        return x * y

    def __hash__(self):
        return hash(self.symbol)


@dataclass
class ConcatOperator:
    symbol = "||"

    def apply(self, x: int, y: int):
        return int(str(x) + str(y))

    def __hash__(self):
        return hash(self.symbol)


def main():
    data = [
        [int(item.rstrip(":")) for item in line.split(" ")]
        for line in (Path(__file__).parent / "input.txt")
        .read_text()
        .strip()
        .splitlines()
    ]
    calibration_result = 0

    for expected, *terms in data:
        operator_count = len(terms) - 1

        for operator_sequence in (
            p
            for pluses in range(0, operator_count + 1)
            for times in range(operator_count - pluses, -1, -1)
            for p in distinct_permutations(
                chain(
                    repeat(PlusOperator(), pluses),
                    repeat(TimesOperator(), times),
                    repeat(ConcatOperator(), operator_count - pluses - times),
                )
            )
        ):
            # Perform calculation
            result = terms[0]
            for i, operator in enumerate(operator_sequence):
                result = operator.apply(result, terms[i + 1])

                if result > expected:
                    break

            # Debug log calculation
            # print(
            #     reduce(
            #         lambda acc, curr: (
            #             str(curr[1])
            #             if curr[0] == 0
            #             else acc + operator_sequence[curr[0] - 1].symbol + str(curr[1])
            #         ),
            #         enumerate(terms),
            #         "",
            #     ),
            #     end="=",
            # )
            # print(result)

            if result == expected:
                calibration_result += result
                break

    print(f"Calibration result: {calibration_result}")


if __name__ == "__main__":
    main()
