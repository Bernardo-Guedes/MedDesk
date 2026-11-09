export function CardQuarto({
  numero,
  status,
  leitosOcupados,
  leitosExistentes
}: {
  numero: number;
  status: string;
  leitosOcupados: number;
  leitosExistentes: number;
}) {
  return (
    <div
      className={`bg-white rounded-lg shadow-md p-4 border-l-4 border border-gray-200 ${
        status === "Ocupado"
          ? "border-l-red-500"
          : status === "Disponível"
            ? "border-l-green-500"
            : "border-l-gray-300"
      }`}
    >
      <div className="flex justify-between">
        <h3 className="text-lg font-medium">Quarto {numero}</h3>

        <p
          className={`text-sm border rounded-full px-2 py-1 ${
            status === "Ocupado"
              ? "text-red-600 border-red-300 bg-red-50"
              : status === "Disponível"
                ? "text-green-600 border-green-300 bg-green-50"
                : "text-muted-foreground"
          }`}
        >
          {status}
        </p>
      </div>
      <p className="text-sm text-muted-foreground text-gray-700">
        {leitosOcupados} de {leitosExistentes} leitos ocupados
      </p>
    </div>
  );
}   