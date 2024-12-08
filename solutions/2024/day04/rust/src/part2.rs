pub fn part2_main() {
    println!("Part 2");
    // println!(
    //     "{}",
    //     WordDetector::for_word("XMAS").detect(include_str!("input.txt").lines())
    // );
    let input_text = include_str!("part2_sample.txt").trim_ascii();
    let input_text = include_str!("input.txt").trim_ascii();
    let line_length: usize = input_text.lines().take(1).map(|l| l.len()).sum();
    let space: Vec<Vec<char>> = input_text
        .lines()
        .map(|l| l.chars().collect::<Vec<char>>())
        .collect();

    let needles = vec![
        vec![
            vec![Some('M'), None, Some('S')],
            vec![None, Some('A'), None],
            vec![Some('M'), None, Some('S')],
        ],
        vec![
            vec![Some('S'), None, Some('S')],
            vec![None, Some('A'), None],
            vec![Some('M'), None, Some('M')],
        ],
        vec![
            vec![Some('S'), None, Some('M')],
            vec![None, Some('A'), None],
            vec![Some('S'), None, Some('M')],
        ],
        vec![
            vec![Some('M'), None, Some('M')],
            vec![None, Some('A'), None],
            vec![Some('S'), None, Some('S')],
        ],
    ];

    let mut sum = 0;
    for space_y in 0..space.len() - 2 {
        for space_x in 0..space[space_y].len() - 2 {
            let mut is_needle_ok = vec![true; needles.len()];
            for (y, line) in space[space_y..space_y + 3].iter().enumerate() {
                for (x, &c) in line[space_x..space_x + 3].iter().enumerate() {
                    for (needle_index, needle) in needles
                        .iter()
                        .enumerate()
                        .filter(|&(i, _)| is_needle_ok[i])
                        .collect::<Vec<_>>()
                    {
                        if let Some(needle_c) = needle[y][x] {
                            if needle_c != c {
                                is_needle_ok[needle_index] = false;
                            }
                        }
                    }
                }
            }
            sum += is_needle_ok.iter().filter(|&&x| x).count();
        }
    }

    println!("{}", sum);
}
