import CardGraph from "@/components/CardGraph";
import CardStat from "@/components/CardStat";
import { ChartColumn } from "lucide-react"

function Home() {
  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <ChartColumn size={40} aria-hidden="true" />
        <h1>Dashboard</h1>
      </div>
      {/* Dados de exemplo da página original. */}
      <div className="mx-5 mt-8 grid max-w-5xl gap-4 sm:grid-cols-2 xl:grid-cols-3 md:mx-8">
        <CardStat titulo="CONSULTAS DE HOJE" numero="8" texto="Pacientes agendados para hoje"/>
        <CardStat titulo="REALIZADAS NO MÊS" numero="124" texto="Total de atendimentos concluídos"/>
        <CardStat titulo="TOTAL AGENDADAS" numero="32" texto="Próximos compromissos marcados"/>
      </div>
      <CardGraph numSeg={12} numTer={25} numQua={18} numQui={30} numSex={22}/>
    </div>
  );
}

export default Home;