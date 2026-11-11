export type TipoStatusAtendimento =
  | "agendada"
  | "emAndamento"
  | "concluida"
  | "cancelada"
  | "naoCompareceu"
  | "neutro";

interface StatusAtendimentoProps {
  texto: string;
  status?: TipoStatusAtendimento;
}

const statusClassNames: Record<TipoStatusAtendimento, string> = {
  agendada: "bg-blue-100 text-blue-800",
  emAndamento: "bg-amber-100 text-amber-800",
  concluida: "bg-emerald-100 text-emerald-800",
  cancelada: "bg-red-100 text-red-800",
  naoCompareceu: "bg-orange-100 text-orange-800",
  neutro: "bg-slate-100 text-slate-700",
};

function StatusAtendimento({
  texto,
  status = "neutro",
}: StatusAtendimentoProps) {
  return (
    <span
      className={`rounded-full px-3 py-1 text-sm font-semibold ${statusClassNames[status]}`}
    >
      {texto}
    </span>
  );
}

export default StatusAtendimento;
