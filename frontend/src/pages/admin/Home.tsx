import CardGraph from "@/components/CardGraph";
import CardConsultasRealizadas from "@/components/CardConsultasRealizadas";
import CardStat from "@/components/CardStat";
import ListItem from "@/components/ListItem";
import BadgeCategoria, { type CategoriaProfissional } from "@/components/ui/BadgeCategoria";
import SelectFiltro from "@/components/ui/SelectFiltro";
import { ChartColumn } from "lucide-react"
import { useState } from "react";

// Totais de exemplo por modalidade.
const atendimentosPorModalidade = [
  { modalidade: "Rotina", total: 155 },
  { modalidade: "Especializado", total: 90 },
  { modalidade: "Emergência", total: 40 },
  { modalidade: "Cirurgia", total: 20 },
  { modalidade: "Internação", total: 35 },
];

// Profissionais e totais de atendimentos de exemplo.
const atendimentosPorProfissional: {
  nome: string;
  categoria: CategoriaProfissional;
  total: number;
}[] = [
  { nome: "Dr. Carlos Sales", categoria: "Especialista", total: 32 },
  { nome: "Dr. Roberto Almeida", categoria: "Especialista", total: 28 },
  { nome: "Dr. Augusto Faria", categoria: "Cirurgião", total: 20 },
  { nome: "Dra. Marina Costa", categoria: "Clínico Geral", total: 35 },
  { nome: "Dra. Helena Martins", categoria: "Especialista", total: 24 },
  { nome: "Dr. Rafael Oliveira", categoria: "Clínico Geral", total: 30 },
  { nome: "Dra. Beatriz Santos", categoria: "Cirurgião", total: 18 },
];

function Home() {
  const [modalidade, setModalidade] = useState(atendimentosPorModalidade[0]);

  return (
    <div className="flex min-h-full flex-col lg:h-full lg:min-h-0">
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <ChartColumn  size={40} />
        Dashboard
      </div>
      <div className="grid flex-1 items-start gap-4 lg:min-h-0 lg:grid-cols-[minmax(0,3fr)_minmax(0,2fr)]">
        <div className="min-w-0 lg:self-stretch lg:overflow-y-auto">
          <div className="mx-5 mt-8 grid items-start gap-4 sm:grid-cols-2 md:mx-8">
            {/* Totais de consultas realizadas de exemplo. */}
            <CardConsultasRealizadas totais={{ dia: "18", semana: "105", mes: "543" }}/>
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
            <hr className="my-4 border-slate-300 sm:col-span-2" />
            {/* Quantidades de quartos de exemplo. */}
            <CardStat titulo="QUARTOS DISPONÍVEIS" numero="6" texto="Quartos disponíveis para internação"/>
            <CardStat titulo="QUARTOS OCUPADOS" numero="18" texto="Quartos atualmente ocupados"/>
          </div>
          {/* Valores de exemplo para a demanda semanal. */}
          <CardGraph numSeg={10} numTer={25} numQua={20} numQui={25} numSex={25} larguraTotal />
        </div>
        <section className="mx-5 mt-8 min-w-0 md:mx-8 lg:ml-0 lg:mt-0 lg:flex lg:min-h-0 lg:flex-col lg:self-stretch lg:border-l lg:border-slate-300 lg:pb-4 lg:pl-4 lg:pt-8">
          <h2 id="atendimentos-profissional" className="text-center text-lg font-semibold text-gray-600">ATENDIMENTOS POR PROFISSIONAL</h2>
          <ul aria-labelledby="atendimentos-profissional" tabIndex={0} className="mt-4 flex max-h-96 flex-col gap-2 overflow-y-auto lg:min-h-0 lg:max-h-none lg:flex-1">
            {atendimentosPorProfissional.map(({ nome, categoria, total }) => (
              <li key={nome}>
                <ListItem
                  nome={nome}
                  destaque={<BadgeCategoria categoria={categoria} />}
                  detalhes={[`${total} atendimentos`]}
                  imagem={null}
                  compacto
                />
              </li>
            ))}
          </ul>
        </section>
      </div>
    </div>
  );
}

export default Home;
