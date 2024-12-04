use crate::detectors::base::BaseModule;
use crate::detectors::WordDetectorModule;

/// Detects a single word vertically in a 2d grid of characters
/// where the cursor moves from upper left to lower right,
/// row by row
pub(crate) struct VerticalModule {
    word: String,
    column_modules: Vec<BaseModule>,
}

impl VerticalModule {
    pub fn for_word(word: &str) -> Self {
        Self {
            word: word.to_string(),
            column_modules: Vec::new(),
        }
    }
}

impl WordDetectorModule for VerticalModule {
    fn accept_char(&mut self, x: usize, _y: usize, c: char) -> usize {
        if self.column_modules.len() < x + 1 {
            self.column_modules
                .push(BaseModule::for_word(self.word.as_str()));
        }

        self.column_modules[x].accept_char(c)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::detectors::WordDetector;

    #[test]
    fn test_part1_sample_xmas() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(VerticalModule::for_word("XMAS"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 1);
    }

    #[test]
    fn test_part1_sample_samx() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(VerticalModule::for_word("SAMX"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 2);
    }
}
