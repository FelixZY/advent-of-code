use std::iter::repeat_n;

fn main() {
    let mut disk = Disk::from_disk_map(include_str!("input.txt"));
    disk.defragment_naive();
    println!("Checksum: {}", disk.checksum())
}

struct Disk {
    blocks: Vec<Option<usize>>,
}

impl Disk {
    fn from_disk_map(disk_map: &str) -> Self {
        Self {
            blocks: disk_map
                .trim()
                .chars()
                .map(|c| c.to_digit(10).unwrap() as usize)
                .enumerate()
                .map(|(i, blocks)| repeat_n(if i % 2 == 0 { Some(i / 2) } else { None }, blocks))
                .flatten()
                .collect(),
        }
    }

    fn defragment_naive(&mut self) {
        let mut left = 0;
        let mut right = self.blocks.len() - 1;

        while left < right {
            while self.blocks[left].is_some() {
                left += 1;
            }
            while self.blocks[right].is_none() {
                right -= 1;
            }

            if left < right {
                self.blocks[left] = self.blocks[right];
                self.blocks[right] = None;
            }
        }
    }

    fn checksum(&self) -> u64 {
        self.blocks
            .iter()
            .enumerate()
            .map(|(i, block)| block.map(|file_id| (file_id * i) as u64))
            .flatten()
            .sum()
    }
}
