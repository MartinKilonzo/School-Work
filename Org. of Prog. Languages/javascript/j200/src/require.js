const require = (path) => {
  const required = document.createElement('script');
  required.src = path;
  return required;
};
