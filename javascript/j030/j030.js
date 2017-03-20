'use strict';

const fs = require('fs');


const SYMBOLS = ['+', '-', '/', '*'];


const evaluate = (value1, action, value2) => {
  switch (action) {
    case '+': return value1 + value2;
      break;
    case '-': return value1 - value2;
      break;
    case '/': return value1 / value2;
      break;
    case '*': return value1 * value2;
      break;
    default: return null;
  }
};

const interpretLine = (line) => {
  const tokens = [];
  line.split(/\b\s+\b/).forEach((token) => { // Apparently javascript doesnt support negative lookbehinds (?<!=)
    tokens.push(token);
  });

  let hold;
  let action = -1;
  tokens.forEach((token) => {
    if (token.match(/\d/)) {
      if (typeof hold !== 'undefined') {
        console.log('Parsing Error');
        return null;
      } else if (action === -1) {
        hold = token;
      } else {
        hold = evaluate(hold, action, token);
        action = -1;
      }
    } else if (token.match(/[+-/*]/)) {
      if (typeof hold === 'undefined') {
        console.log('Parsing Error');
        return null;
      }
      action = token;
    }
  });
  return hold;
};


fs.readFile('./input.txt', 'utf8', (err, data) => {
  if (err) throw err;

  const tokens = [];
  data.split(/[\n\r]+/).forEach((line) => {
    console.log(interpretLine(line));
  });
});
