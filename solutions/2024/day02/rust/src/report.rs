use std::str::FromStr;

pub struct Report {
    data: Vec<i64>,
}

impl Report {
    pub fn parse(line: &str) -> Self {
        Self {
            data: line
                .split_whitespace()
                .map(|s| {
                    i64::from_str(s).expect(format!("Invalid report: {} is not a i64", s).as_str())
                })
                .collect(),
        }
    }

    pub fn is_safe(&self) -> bool {
        if self.data.len() < 2 {
            // Levels are not _increasing or decreasing_
            return false;
        }

        let mut prev_level = *self.data.first().unwrap();
        let is_increasing = self.data[1] - self.data[0] > 0;

        for i in 1..self.data.len() {
            let diff = self.data[i] - prev_level;
            if diff == 0
                || diff.abs() > 3
                || is_increasing && diff < 0
                || !is_increasing && diff > 0
            {
                return false;
            }
            prev_level = self.data[i]
        }

        true
    }
}
