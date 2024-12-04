use crate::detectors::diagonal::DiagonalModule;
use crate::detectors::horizontal::HorizontalModule;
use crate::detectors::vertical::VerticalModule;

mod base;
mod diagonal;
mod horizontal;
mod vertical;

pub trait WordDetectorModule {
    /// Returns the number of detections on the given character
    fn accept_char(&mut self, x: usize, y: usize, c: char) -> usize;
}

pub struct WordDetector {
    modules: Vec<Box<dyn WordDetectorModule>>,
}

impl WordDetector {
    pub fn for_word(word: &str) -> Self {
        // This won't work for Unicode strings but is sufficient for this task:
        // https://stackoverflow.com/a/27996791/1137077
        let word_rev = &word.chars().rev().collect::<String>();
        Self {
            modules: vec![
                Box::new(HorizontalModule::for_word(word)),
                Box::new(HorizontalModule::for_word(word_rev)),
                Box::new(VerticalModule::for_word(word)),
                Box::new(VerticalModule::for_word(word_rev)),
                Box::new(DiagonalModule::for_word(word)),
                Box::new(DiagonalModule::for_word(word_rev)),
            ],
        }
    }

    #[cfg(test)]
    pub fn for_modules(modules: Vec<Box<dyn WordDetectorModule>>) -> Self {
        // This won't work for Unicode strings but is sufficient for this task:
        // https://stackoverflow.com/a/27996791/1137077
        Self { modules }
    }

    pub fn detect<'a>(&mut self, lines: impl Iterator<Item = &'a str>) -> usize {
        let mut detections = 0;
        lines.enumerate().for_each(|(y, line)| {
            line.chars().enumerate().for_each(|(x, c)| {
                self.modules.iter_mut().for_each(|module| {
                    detections += module.accept_char(x, y, c);
                })
            })
        });
        detections
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part1_sample() {
        let mut detector = WordDetector::for_word("XMAS");
        let count = detector.detect(include_str!("part1_sample.txt").lines().into_iter());

        assert_eq!(count, 18);
    }
}
