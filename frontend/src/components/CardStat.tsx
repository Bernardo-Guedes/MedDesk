interface CardStatProps {
  titulo: string;
  numero: string;
  texto: string;
}

function CardStat({ titulo, numero, texto }: CardStatProps){
    return(
        <div className="bg-white rounded-2xl border-3 border-principal flex flex-col gap-2 py-8 px-10">
            <span className="text-lg text-gray-600 font-semibold">{titulo}</span>
            <span className="text-4xl text-principal font-bold">{numero}</span>
            <span className="text-sm text-gray-400">{texto}</span>
        </div>
    );
}

export default CardStat;