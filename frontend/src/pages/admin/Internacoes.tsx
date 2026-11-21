import { CardQuarto } from "@/components/CardQuarto";
import { CardStatInternacao } from "@/components/CardStatInternacao";
import SelectFiltro from "@/components/ui/SelectFiltro";
import { BedDouble } from "lucide-react";
import { useState } from "react";

function Internacoes(){
    const [status, setStatus] = useState("");
    // Dados de exemplo dos quartos.
    const quartos = [
      { numero: 101, leitosOcupados: 2, leitosExistentes: 2 },
      { numero: 102, leitosOcupados: 0, leitosExistentes: 2 },
      { numero: 201, leitosOcupados: 1, leitosExistentes: 1 },
      { numero: 202, leitosOcupados: 0, leitosExistentes: 1 },
      { numero: 301, leitosOcupados: 1, leitosExistentes: 1 },
      { numero: 302, leitosOcupados: 0, leitosExistentes: 1 },
    ];
    const quartosFiltrados = quartos
      .map((quarto) => ({
        ...quarto,
        status: quarto.leitosOcupados > 0 ? "Ocupado" : "Disponível",
      }))
      .filter((quarto) => !status || quarto.status === status);

    return(
        <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <BedDouble size={40} />
        Quartos
      </div>
      {/* Indicadores de exemplo dos quartos. */}
      <div className="flex gap-3 mt-8 mx-5">
        <CardStatInternacao text="Número de quartos" number={24} />
        <CardStatInternacao text="Quartos disponíveis" number={6} />    
        <CardStatInternacao text="Pacientes internados" number={18} />
      </div>
      <div className="mt-8 mx-5 bg-white rounded-lg p-5">
        <h2 className="text-2xl font-semibold">Ocupação dos quartos</h2>
        <div className="mt-4 max-w-xs">
          <SelectFiltro
            id="filtro-status-quarto"
            rotulo="Situação do quarto"
            valor={status}
            onChange={setStatus}
            opcoes={[
              { valor: "", texto: "Todos os quartos" },
              { valor: "Disponível", texto: "Disponíveis" },
              { valor: "Ocupado", texto: "Ocupados" },
            ]}
          />
        </div>
        {quartosFiltrados.length ? (
        <div className="grid grid-cols-1 gap-4 mt-4 md:grid-cols-2 xl:grid-cols-3">
            {quartosFiltrados.map((quarto) => (
              <CardQuarto
                key={quarto.numero}
                numero={quarto.numero}
                status={quarto.status}
                leitosOcupados={quarto.leitosOcupados}
                leitosExistentes={quarto.leitosExistentes}
              />
            ))}
        </div>
        ) : (
          <p role="status" className="mt-4 text-slate-600">Nenhum quarto encontrado para a situação selecionada.</p>
        )}
      </div>
    </div>
    );
}

export default Internacoes;