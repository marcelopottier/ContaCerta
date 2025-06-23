var config = { responsive: true };

// Séries por data (dia do mês)
var datas = ['20/02', '21/02', '22/02', '23/02', '24/02', '25/02', '26/02', '27/02', '28/02', '01/03'];

// Despesas totais por dia
var despesas = [0, 49.9 * 4, 0, 89.99, 39.9, 0, 199, 120, 320.4, 0];
// 21/02 teve 4 vezes R$ 49,90 → 199.6

// Receitas totais por dia
var receitas = [4000, 0, 0, 0, 0, 1200, 0, 0, 350, 0];

var traceDespesas = {
    x: datas,
    y: despesas,
    mode: 'lines+markers',
    name: 'Despesas',
    line: { color: 'red' },
    connectgaps: true
};

var traceReceitas = {
    x: datas,
    y: receitas,
    mode: 'lines+markers',
    name: 'Receitas',
    line: { color: 'green' },
    connectgaps: true
};

var data = [traceDespesas, traceReceitas];

var layout = {
    title: 'Fluxo de Transações (Fev–Mar)',
    xaxis: { title: 'Data', tickangle: -45 },
    yaxis: { title: 'Valor (R$)', autorange: true },
    margin: { t: 50, b: 80, l: 50, r: 20 },
    showlegend: false,
};

Plotly.newPlot('lineChart', data, layout, config);

// Gráfico de pizza
var data2 = [{
    values: [490.3, 39.9, 27.5, 89.99, 199],
    labels: ['Alimentação', 'Entretenimento', 'Transporte', 'Saúde', 'Educação'],
    type: 'pie',
    hole: .6
}];

var layout = {
    showlegend: false,
};

Plotly.newPlot('pieChart', data2, layout, config);