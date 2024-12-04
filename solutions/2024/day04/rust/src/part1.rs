use crate::vectorized_string::string_from;

pub fn part1_main() {
    println!("Part 1");
    // println!(
    //     "{}",
    //     WordDetector::for_word("XMAS").detect(include_str!("input.txt").lines())
    // );
    let input_text = include_str!("detectors/part1_sample.txt").trim_ascii();
    let input_text = include_str!("input.txt").trim_ascii();
    let line_length: usize = input_text.lines().take(1).map(|l| l.len()).sum();
    let space: Vec<Vec<char>> = input_text
        .lines()
        .map(|l| l.chars().collect::<Vec<char>>())
        .collect();
    let origins = input_text
        .chars()
        .filter(|x| x.is_ascii_alphabetic())
        .enumerate()
        .filter(|&(_, c)| c == 'X')
        .map(|(i, _)| ((i / line_length) as isize, (i % line_length) as isize))
        .collect::<Vec<(isize, isize)>>();

    let result_count = origins
        .iter()
        .map(|origin| {
            (-1..=1)
                .map(|outer| (-1..=1).map(move |inner| (outer, inner)))
                .flatten()
                .filter(|&(y, x)| y != x || y != 0 && x != 0)
                .map(|direction| {
                    string_from(&space, origin, &direction, "XMAS".len()).filter(|s| s == "XMAS")
                })
                .flatten()
        })
        .flatten()
        .count();

    println!("{}", result_count);
}
