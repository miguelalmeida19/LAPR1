set terminal png
set output grafico.png 
set term dumb
set title "Teste Plot"
set xlabel "X"
set ylabel "Y"
set style data linespoints
f(x) = 2 * sin(x)
g(x) = 2 * cos(x)
plot f(x) title 'sin(x)' with lines linestyle 1, \
     g(x) title 'cos(x)' with lines linestyle 2
