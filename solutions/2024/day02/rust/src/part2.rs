use crate::report::Report;

pub fn part2_main() {
    println!("Safe reports: {}", get_safe_report_count());
}

fn get_safe_report_count() -> usize {
    let input = include_str!("input.txt");
    let reports: Vec<Report> = input.lines().map(|line| Report::parse(line)).collect();
    reports.iter().filter(|r| r.is_safe(1)).count()
}

#[cfg(test)]
mod tests {
    use crate::part2::get_safe_report_count;

    #[test]
    fn test_part2() {
        assert_eq!(get_safe_report_count(), 674);
    }
}
