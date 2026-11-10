import CardGraph from "@/components/CardGraph";
import CardStat from "@/components/CardStat";
import { ChartColumn } from "lucide-react"

function Home() {
  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <ChartColumn  size={40} />
        Dashboard
      </div>
      {/* Indicadores e gráfico com dados de exemplo. */}
      <div className="mt-8 flex gap-4 ms-8">
        <CardStat titulo="CONSULTAS DE HOJE" numero="22" texto="Pacientes agendados pra hoje"/>
        <CardStat titulo="REALIZADAS NO MÊS" numero="543" texto="Total de atendimentos concluídos"/>
        <CardStat titulo="TOTAL AGENDADAS" numero="59" texto="Próximos compromissos marcados"/>
      </div>
      <CardGraph numSeg={10} numTer={25} numQua={20} numQui={25} numSex={25}/>
    </div>
  );
}

export default Home;
