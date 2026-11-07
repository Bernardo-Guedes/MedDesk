interface CardGraphProps {
  numSeg: number;
  numTer: number;
  numQua: number;
  numQui: number;
  numSex: number;
}

function CardGraph({ numSeg, numTer, numQua, numQui, numSex }: CardGraphProps) {
  const dados = [
    { dia: "Seg", valor: numSeg },
    { dia: "Ter", valor: numTer },
    { dia: "Qua", valor: numQua },
    { dia: "Qui", valor: numQui },
    { dia: "Sex", valor: numSex },
  ];

  const maiorValor = Math.max(...dados.map((item) => item.valor), 1);

  return (
    <div className="bg-white rounded-2xl border-3 border-principal flex flex-col gap-2 pb-8 px-10 max-w-155 ms-8 mt-8">
      <span className="text-xl text-principal font-semibold my-8">
        Demanda da semana
      </span>
      <div className="flex items-end justify-around gap-4 h-60 border-b border-gray-300 pb-4">
        {dados.map((item) => {
          const altura = (item.valor / maiorValor) * 100;

          return (
            <div
              key={item.dia}
              className="flex flex-col items-center justify-end gap-4 h-full flex-1"
            >
              <div
                className="group relative w-full flex justify-center"
                style={{ height: `${altura}%` }}
              >
                {/* Número animado */}
                <span className="absolute -top-2 text-sm font-semibold text-black opacity-0 -translate-y-1 transition-all duration-300 ease-out group-hover:opacity-100 group-hover:-translate-y-5">
                  {item.valor}
                </span>

                {/* Barra */}
                <div
                  className="w-full max-w-15 h-full bg-principal hover:bg-blue-500 rounded-t-lg transition-colors duration-200"
                  title={`${item.valor} demandas`}
                />
              </div>

              {/* Dia da semana */}
              <span className="text-sm font-semibold text-gray-700">
                {item.dia}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default CardGraph;
