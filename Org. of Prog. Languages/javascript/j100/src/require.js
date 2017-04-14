const require = (path) => {
  const required = document.createElement('script');
  if (path.includes('http')) required.src = path;
  else if (path.includes('.js')) required.src = `./scripts/${path}`;
  else if (path.includes('.css')) required.src = `./styles/${path}`;
  document.head.append(required);
  return required;
};
