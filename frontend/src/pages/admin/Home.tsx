import CardGraph from "@/components/CardGraph";
import CardConsultasRealizadas from "@/components/CardConsultasRealizadas";
import CardStat from "@/components/CardStat";
import SelectFiltro from "@/components/ui/SelectFiltro";
import { ChartColumn } from "lucide-react"
import { useState } from "react";

const atendimentosPorModalidade = [
  { modalidade: "Rotina", total: 155 },
  { modalidade: "Especializado", total: 90 },
  { modalidade: "Emergência", total: 40 },
  { modalidade: "Cirurgia", total: 20 },
  { modalidade: "Internação", total: 35 },
];

function Home() {
  const [modalidade, setModalidade] = useState(atendimentosPorModalidade[0]);

  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <ChartColumn  size={40} />
        Dashboard
      </div>
      {/* Indicadores e gráfico com dados de exemplo. */}
      <div className="mx-5 mt-8 grid max-w-5xl items-start gap-4 sm:grid-cols-2 md:mx-8">
        <CardConsultasRealizadas totais={{ dia: "18", semana: "105", mes: "543" }}/>
        <CardStat titulo="TOTAL AGENDADAS" numero="59" texto="Próximos compromissos marcados"/>
        <div className="flex flex-col gap-3">
          <SelectFiltro
            id="filtro-modalidade"
            rotulo="Modalidade do atendimento"
            valor={modalidade.modalidade}
            onChange={(valor) => {
              const selecionada = atendimentosPorModalidade.find((item) => item.modalidade === valor);
              if (selecionada) setModalidade(selecionada);
            }}
            opcoes={atendimentosPorModalidade.map((item) => ({
              valor: item.modalidade,
              texto: item.modalidade,
            }))}
          />
          <CardStat
            titulo={`ATENDIMENTOS - ${modalidade.modalidade.toUpperCase()}`}
            numero={String(modalidade.total)}
            texto="Total de atendimentos"
          />
        </div>
      </div>
      <CardGraph numSeg={10} numTer={25} numQua={20} numQui={25} numSex={25}/>
    </div>
  );
}

export default Home;
