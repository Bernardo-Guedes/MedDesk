import { useId, useState } from "react";
import CardStat from "@/components/CardStat";
import SelectFiltro from "@/components/ui/SelectFiltro";

type Periodo = "dia" | "semana" | "mes";

interface CardConsultasRealizadasProps {
  totais: Record<Periodo, string>;
}

const opcoes = [
  { valor: "dia", texto: "Hoje" },
  { valor: "semana", texto: "Esta semana" },
  { valor: "mes", texto: "Este mês" },
];

const titulos: Record<Periodo, string> = {
  dia: "REALIZADAS HOJE",
  semana: "REALIZADAS NA SEMANA",
  mes: "REALIZADAS NO MÊS",
};

function CardConsultasRealizadas({ totais }: CardConsultasRealizadasProps) {
  const [periodo, setPeriodo] = useState<Periodo>("mes");
  const id = useId();

  return (
    <div className="flex flex-col gap-3">
      <SelectFiltro
        id={id}
        rotulo="Período das consultas realizadas"
        valor={periodo}
        onChange={(valor) => {
          if (valor === "dia" || valor === "semana" || valor === "mes") {
            setPeriodo(valor);
          }
        }}
        opcoes={opcoes}
      />
      <CardStat titulo={titulos[periodo]} numero={totais[periodo]} texto="Consultas concluídas" />
    </div>
  );
}

export default CardConsultasRealizadas;
