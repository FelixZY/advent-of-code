use criterion::{criterion_group, criterion_main, Criterion};

fn benchmarks(c: &mut Criterion) {
    c.bench_function("println bench", |b| {
        b.iter(|| {
            println!("Hello, world!");
        });
    });
}

criterion_group!(
    name = benches;
    config = Criterion::default();
    targets = benchmarks
);
criterion_main!(benches);
