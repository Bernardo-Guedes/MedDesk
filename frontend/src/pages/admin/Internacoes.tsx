import { CardQuarto } from "@/components/CardQuarto";
import { CardStatInternacao } from "@/components/CardStatInternacao";
import { ClipboardList } from "lucide-react";

function Internacoes(){
    return(
        <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <ClipboardList size={40} />
        Internações
      </div>
      <div className="flex gap-3 mt-8 mx-5">
        <CardStatInternacao text="Número de quartos" number={24} />
        <CardStatInternacao text="Quartos disponíveis" number={6} />    
        <CardStatInternacao text="Pacientes internados" number={18} />
      </div>
      <div className="mt-8 mx-5 bg-white rounded-lg p-5">
        <h2 className="text-2xl font-semibold">Ocupação dos quartos</h2>
        <div className="grid grid-cols-3 gap-4 mt-4">
            <CardQuarto numero={101} status="Ocupado" leitosOcupados={4} leitosExistentes={4} />
            <CardQuarto numero={102} status="Disponível" leitosOcupados={0} leitosExistentes={4} />
            <CardQuarto numero={201} status="Ocupado" leitosOcupados={4} leitosExistentes={4} />
            <CardQuarto numero={202} status="Disponível" leitosOcupados={2} leitosExistentes={4} />
            <CardQuarto numero={301} status="Ocupado" leitosOcupados={4} leitosExistentes={4} />
            <CardQuarto numero={302} status="Ocupado" leitosOcupados={4} leitosExistentes={4} />
        </div>
      </div>
    </div>
    );
}

export default Internacoes;