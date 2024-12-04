pub(crate) struct BaseModule {
    characters: Vec<char>,
    character_index: usize,
}

impl BaseModule {
    pub(crate) fn for_word(word: &str) -> Self {
        Self {
            characters: word.chars().collect(),
            character_index: 0,
        }
    }

    pub(crate) fn reset_index(&mut self) {
        self.character_index = 0;
    }

    pub(crate) fn accept_char(&mut self, c: char) -> usize {
        if self.character_index > 0 && self.characters[self.character_index] != c {
            self.reset_index()
        }

        if self.characters[self.character_index] == c {
            self.character_index += 1;
            if self.characters.len() == self.character_index {
                self.reset_index();
                return 1;
            }
        }
        0
    }
}
