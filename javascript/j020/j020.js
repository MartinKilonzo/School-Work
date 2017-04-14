const fs = require('fs');

const PUNCTUATION = /[,;|()[\]{}]/;

const getType = (numStr) => {
  if (numStr.match(/[\d]+\.[\d]/)) return `f ${numStr}`;
  else if (numStr.match(PUNCTUATION)) return `s ${numStr}`;
  return `i ${numStr}`;
};

const parse = (data) => {
  const numList = [];
  data.split(/[\n\r]+/).forEach((line) => {
    line.split(/\b\s+\b(?!=)/).forEach((token) => { // Apparently javascript doesnt support negative lookbehinds (?<!=)
      if (token.match(/\.\d+/)) numList.push(`f ${token}`);
      else if (token.match(/[(),;[]|{}]/)) numList.push(`s ${token}`);
      else if (token.match(/=/)) numList.push(`v ${token}`);
      else if (token.match(/\d+/)) numList.push(`i ${token}`);
    });
  });
  return numList;
}


fs.readFile('./nums.txt', 'utf8', (err, data) => {
  if (err) throw err;
  // init
  const program = {
    functionDeclarations: [],
    expressions: [],
    factor: [],
    integers: [],
    floats: [],
    symbols: [],
  };
  // Parse data
  parse(data).forEach((term) => {
    switch (term[0]) {
      case 'i':
        program.integers.push(term.slice(2));
        break;
      case 'f':
        program.floats.push(term.slice(2));
        break;
      case 's':
        program.symbols.push(term.slice(2));
        break;
      case 'v':
        program.expressions.push(term.slice(2));
        break;
      default:
        break;
    }
  });
  console.log(program);
});
