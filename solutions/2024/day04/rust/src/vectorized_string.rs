pub fn string_from(
    space: &Vec<Vec<char>>,
    origin: &(isize, isize),
    direction: &(isize, isize),
    length: usize,
) -> Option<String> {
    (0..length as isize)
        .map(|offset| {
            let outer = origin.0 + direction.0 * offset;
            let inner = origin.1 + direction.1 * offset;

            if outer < 0
                || outer >= space.len() as isize
                || inner < 0
                || inner >= space[outer as usize].len() as isize
            {
                None
            } else {
                Some(space[outer as usize][inner as usize])
            }
        })
        .collect::<Option<String>>()
        .filter(|x| x.len() == length)
}
