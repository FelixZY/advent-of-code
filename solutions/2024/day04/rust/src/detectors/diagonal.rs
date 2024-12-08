use crate::detectors::base::BaseModule;
use crate::detectors::WordDetectorModule;

/// Detects a single word diagonally in a 2d grid of characters
/// where the cursor moves from upper left to lower right,
/// row by row
pub(crate) struct DiagonalModule {
    left_module: DiagonalLeftModule,
    right_module: DiagonalRightModule,
}

impl DiagonalModule {
    pub fn for_word(word: &str) -> Self {
        Self {
            left_module: DiagonalLeftModule::for_word(word),
            right_module: DiagonalRightModule::for_word(word),
        }
    }
}

impl WordDetectorModule for DiagonalModule {
    fn accept_char(&mut self, x: usize, y: usize, c: char) -> usize {
        self.left_module.accept_char(x, y, c) + self.right_module.accept_char(x, y, c)
    }
}

struct DiagonalLeftModule {
    word: String,
    modules: Vec<BaseModule>,
    current_row: usize,
}

impl DiagonalLeftModule {
    pub fn for_word(word: &str) -> Self {
        Self {
            word: word.to_string(),
            modules: Vec::new(),
            current_row: 0,
        }
    }
}

impl WordDetectorModule for DiagonalLeftModule {
    fn accept_char(&mut self, x: usize, y: usize, c: char) -> usize {
        if self.current_row != y {
            self.current_row = y;
            let x_max = self.modules.len();
            self.modules[y % x_max].reset_index()
        }

        if self.modules.len() < x + 1 {
            self.modules.push(BaseModule::for_word(self.word.as_str()));
        }

        let module_index = (x + y) % self.modules.len();
        self.modules[module_index].accept_char(c)
    }
}

struct DiagonalRightModule {
    word: String,
    modules: Vec<BaseModule>,
    current_row: usize,
}

impl DiagonalRightModule {
    pub fn for_word(word: &str) -> Self {
        Self {
            word: word.to_string(),
            modules: Vec::new(),
            current_row: 0,
        }
    }
}

impl WordDetectorModule for DiagonalRightModule {
    fn accept_char(&mut self, x: usize, y: usize, c: char) -> usize {
        if self.current_row != y {
            self.current_row = y;
            let x_max = self.modules.len();
            self.modules[x_max - (y % x_max)].reset_index()
        }

        if self.modules.len() < x + 1 {
            self.modules.push(BaseModule::for_word(self.word.as_str()));
        }

        let module_index = (x
            .checked_sub(y)
            .unwrap_or_else(|| self.modules.len() + x - y))
            % self.modules.len();
        self.modules[module_index].accept_char(c)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::detectors::WordDetector;

    #[test]
    fn test_left_part1_sample_xmas() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalLeftModule::for_word("XMAS"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 1);
    }

    #[test]
    fn test_left_part1_sample_samx() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalLeftModule::for_word("SAMX"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 3);
    }

    #[test]
    fn test_right_part1_sample_xmas() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalRightModule::for_word("XMAS"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 1);
    }

    #[test]
    fn test_right_part1_sample_samx() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalRightModule::for_word("SAMX"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 4);
    }

    #[test]
    fn test_part1_sample_xmas() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalModule::for_word("XMAS"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 2);
    }

    #[test]
    fn test_part1_sample_samx() {
        let mut detector =
            WordDetector::for_modules(vec![Box::new(DiagonalModule::for_word("SAMX"))]);
        let count = detector.detect(include_str!("part1_sample.txt").lines());

        assert_eq!(count, 7);
    }
}
