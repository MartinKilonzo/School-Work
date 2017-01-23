/* globals examdatabase */

$(document).ready(() => {
  let questionIndex = -1;
  let correctQuestions = 0;
  // Math.random.seed();
  const getRandomQuestion = () => {
    const min = 0;
    const max = examdatabase.questions.length;

    questionIndex = Math.floor(min + (Math.random() * (max - min)));
    $('#answer').text('');
  };

  const showNextQuestion = () => {
    getRandomQuestion();
    const question = examdatabase.questions[questionIndex].question;
    $('#question').text(question);
  };

  const showNextAnswer = () => {
    const answer = examdatabase.questions[questionIndex].answer;
    $('#answer').text(answer);
  };

  const addCorrentQuestion = () => {
    correctQuestions += 1;
    $('#correct').text(correctQuestions);
  };

  showNextQuestion();
  addCorrentQuestion();

  $('#question').siblings('button').on('click', event => showNextQuestion());
  $('#answer').siblings('button').on('click', event => showNextAnswer());
  $('#correct').siblings('button').on('click', event => addCorrentQuestion());
});
