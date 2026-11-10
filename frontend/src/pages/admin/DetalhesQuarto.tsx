import { useState } from "react";
import { BedDouble } from "lucide-react";
import { Link, useParams } from "react-router";
import { DangerButton } from "@/components/ui/button";
import StatusAtendimento from "@/components/ui/StatusAtendimento";

interface Quarto {
  numero: number;
  leitos: number;
  pacientes: { nome: string; leito: string; entrada: string }[];
}

// Dados de exemplo dos quartos e pacientes.
const quartos: Quarto[] = [
  {
    numero: 101,
    leitos: 2,
    pacientes: [
      { nome: "Alice Martins", leito: "Leito 1", entrada: "05/10/2026" },
      { nome: "Pedro Alves", leito: "Leito 2", entrada: "06/10/2026" },
    ],
  },
  { numero: 102, leitos: 2, pacientes: [] },
  {
    numero: 201,
    leitos: 1,
    pacientes: [{ nome: "Luiza Gomes", leito: "Leito 1", entrada: "06/10/2026" }],
  },
  { numero: 202, leitos: 1, pacientes: [] },
  {
    numero: 301,
    leitos: 1,
    pacientes: [{ nome: "Mariana Costa", leito: "Leito 1", entrada: "07/10/2026" }],
  },
  { numero: 302, leitos: 1, pacientes: [] },
];

function ConteudoQuarto({ quarto }: { quarto: Quarto }) {
  const [pacientes, setPacientes] = useState(quarto.pacientes);
  const ocupado = pacientes.length > 0;

  return (
    <>
      <div className="flex flex-col gap-3 border-b border-slate-200 pb-6 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h2 className="text-2xl font-semibold">Quarto {quarto.numero}</h2>
          <p className={`mt-2 font-bold ${ocupado ? "text-red-700" : "text-emerald-700"}`}>
            {ocupado ? "Ocupado" : "Disponível"}
          </p>
        </div>
        <p className="font-semibold text-slate-600">
          {pacientes.length} de {quarto.leitos} {quarto.leitos === 1 ? "leito ocupado" : "leitos ocupados"}
        </p>
      </div>
      {ocupado ? (
        <>
          <h3 className="mb-3 mt-6 text-lg font-semibold text-principal">Pacientes neste quarto</h3>
          <div className="flex flex-col gap-4">
            {pacientes.map((paciente) => (
              <article key={paciente.leito} className="flex flex-col items-start gap-4 rounded-2xl border border-slate-200 p-5 shadow-sm sm:flex-row sm:items-center sm:justify-between">
                <div className="flex min-w-0 flex-col gap-3">
                  <span className="font-semibold text-slate-800">{paciente.nome}</span>
                  <div className="flex flex-wrap gap-2">
                    <StatusAtendimento texto={`Entrada: ${paciente.entrada}`} />
                  </div>
                </div>
                <DangerButton
                  texto="Remover"
                  ariaLabel={`Remover ${paciente.nome} do quarto`}
                  onClick={() => setPacientes((atuais) => atuais.filter((atual) => atual.leito !== paciente.leito))}
                />
              </article>
            ))}
          </div>
        </>
      ) : (
        <p className="py-5 text-slate-600">Este quarto está disponível e não possui pacientes internados.</p>
      )}
    </>
  );
}

function DetalhesQuarto() {
  const { numeroQuarto } = useParams();
  const quarto = quartos.find((item) => String(item.numero) === numeroQuarto);

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <BedDouble size={40} aria-hidden="true" />
        <h1>{quarto ? `Quarto ${quarto.numero}` : "Detalhes do Quarto"}</h1>
      </div>
      <div className="mx-auto max-w-5xl p-5 md:p-8">
        <section className="rounded-2xl bg-white p-6 shadow-sm md:p-8">
          {quarto ? (
            <ConteudoQuarto key={quarto.numero} quarto={quarto} />
          ) : (
            <>
              <h2 className="text-2xl font-semibold">Quarto não encontrado</h2>
              <p role="alert" className="py-5 text-red-700">
                Não foi possível encontrar esse quarto. Volte para Internações e selecione um quarto da lista.
              </p>
            </>
          )}
          <Link to="/admin/internacoes" className="mt-6 inline-flex min-h-11 items-center justify-center rounded-full bg-principal px-6 py-3 font-semibold text-white transition-colors hover:bg-principal-hover">
            Voltar para Internações
          </Link>
        </section>
      </div>
    </div>
  );
}

export default DetalhesQuarto;
