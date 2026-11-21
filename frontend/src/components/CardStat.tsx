interface CardStatProps {
  titulo: string;
  numero: string;
  texto: string;
  variante?: "padrao" | "disponivel" | "ocupado";
}

const estilos = {
  padrao: { card: "bg-white border-principal", numero: "text-principal" },
  disponivel: { card: "bg-green-50 border-green-500", numero: "text-green-700" },
  ocupado: { card: "bg-red-50 border-red-500", numero: "text-red-700" },
};

function CardStat({ titulo, numero, texto, variante = "padrao" }: CardStatProps){
    return(
        <div className={`rounded-2xl border-3 flex flex-col gap-2 py-8 px-10 ${estilos[variante].card}`}>
            <span className="text-lg text-gray-600 font-semibold">{titulo}</span>
            <span className={`text-4xl font-bold ${estilos[variante].numero}`}>{numero}</span>
            <span className="text-sm text-gray-400">{texto}</span>
        </div>
    );
}

export default CardStat;