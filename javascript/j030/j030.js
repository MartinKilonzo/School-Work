const fs = require('fs');


const evaluate = (value1, action, value2) => {
  const a = parseInt(value1, 10);
  const b = parseInt(value2, 10);
  switch (action) {
    case '+':
      return a + b;
    case '-':
      return a - b;
    case '/':
      return a / b;
    case '*':
      return a * b;
    default:
      return null;
  }
};

const interpretLine = (line) => {
  const tokens = [];
  line.split(/\s+/).forEach((token) => {
    if (typeof token !== 'undefined' && token.length > 0) {
      tokens.push(token);
    }
  });

  let hold;
  let action = -1;
  tokens.forEach((token) => {
    if (token.match(/\d/)) {
      if (action === -1) {
        hold = token;
      } else {
        hold = evaluate(hold, action, token);
        action = -1;
      }
    } else if (token.match(/[+-/*]/)) {
      if (typeof hold === 'undefined') {
        return null;
      }
      action = token;
    }
  });
  return hold;
};


fs.readFile('./input.txt', 'utf8', (err, data) => {
  if (err) throw err;

  data.split(/[\n\r]+/).forEach((line) => {
    console.log(interpretLine(line));
  });
});
