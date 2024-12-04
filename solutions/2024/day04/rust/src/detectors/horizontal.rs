use crate::detectors::base::BaseModule;
use crate::detectors::WordDetectorModule;

/// Detects a single word horizontally in a 2d grid of characters
/// where the cursor moves from upper left to lower right,
/// row by row
pub(crate) struct HorizontalModule {
    base: BaseModule,
    current_row: usize,
}

impl HorizontalModule {
    pub fn for_word(word: &str) -> Self {
        Self {
            base: BaseModule::for_word(word),
            current_row: 0,
        }
    }
}

impl WordDetectorModule for HorizontalModule {
    fn accept_char(&mut self, _x: usize, y: usize, c: char) -> usize {
        if y != self.current_row {
            self.base.reset_index();
            self.current_row = y;
        }

        self.base.accept_char(c)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::detectors::WordDetector;

    #[test]
    fn test_part1_sample_xmas() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(HorizontalModule::for_word("XMAS"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 3);
    }

    #[test]
    fn test_part1_sample_samx() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(HorizontalModule::for_word("SAMX"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 2);
    }
}
