function classes = p3(X_train, Y_train, X_test, k)
classes = []
for iTest = 1:size(X_test, 1)
    test = X_test(iTest,:)

    dist = sqrt(sum((X_train - test).^2, 2))

    nearest = sortrows([dist Y_train], 1)
    
    classes(end + 1) = mode(nearest(1:k, 2))
end