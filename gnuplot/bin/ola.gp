# To view in X:

# To print on a PostScript printer:
set terminal pngcairo


set term 

set title "Teste Plot"
set xlabel "X"
set ylabel "Y"

set style data linespoints

f(x) = 2 * sin(x)
g(x) = 2 * cos(x)

# Plot
plot f(x) title 'sin(x)' with lines linestyle 1, \
     g(x) title 'cos(x)' with lines linestyle 2


# Executar este script gnuplot na linha de comandos
# C:\ gnuplot exemploGnuplot.gp

# Executar este script gnuplot em Java:
#       Runtime  rt = Runtime.getRuntime(); 
#	Process prcs = rt.exec("gnuplot exemploGnuplot.gp");