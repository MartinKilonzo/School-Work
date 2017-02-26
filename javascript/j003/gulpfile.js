const gulp = require('gulp');
const browserSync = require('browser-sync').create();
const browserify = require('gulp-browserify');

gulp.task('start', () => {
  browserSync.init({
    server: {
      baseDir: 'app',
    },
  });
});

gulp.task('js', () => {
  return gulp.src('src/j003.js')
    .pipe(browserify({
      insertGlobals: true,
    }))
    .pipe(gulp.dest('app/'))
    .pipe(browserSync.reload({
      stream: true,
      debug: true, //!gulp.env.production,
    }));
});

gulp.task('css', () => {
  return gulp.src('src/styles/**/*.css')
    .pipe(gulp.dest('app/styles'))
    .pipe(browserSync.reload({
      stream: true,
    }));
});

gulp.task('html', () => {
  return gulp.src('src/**/*.html')
    .pipe(gulp.dest('app/'))
    .pipe(browserSync.reload({
      stream: true,
    }));
});

gulp.task('default', ['start', 'js', 'css', 'html'], () => {
  gulp.watch('src/**/*.js', ['js']);
  gulp.watch('src/styles/**/*.css', ['css']);
  gulp.watch('src/**/*.html', ['html']);
});
