use crate::report::Report;

pub fn part1_main() {
    let input = include_str!("input.txt");
    let reports: Vec<Report> = input.lines().map(|line| Report::parse(line)).collect();
    let safe_count = reports.iter().filter(|r| r.is_safe()).count();

    println!("Safe reports: {}", safe_count);
}
