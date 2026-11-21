import CardGraph from "@/components/CardGraph";
import CardConsultasRealizadas from "@/components/CardConsultasRealizadas";
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
      <div className="mx-5 mt-8 grid max-w-5xl items-start gap-4 sm:grid-cols-2 md:mx-8">
        <CardConsultasRealizadas totais={{ dia: "18", semana: "105", mes: "543" }}/>
        <CardStat titulo="TOTAL AGENDADAS" numero="59" texto="Próximos compromissos marcados"/>
      </div>
      <CardGraph numSeg={10} numTer={25} numQua={20} numQui={25} numSex={25}/>
    </div>
  );
}

export default Home;
