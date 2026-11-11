import { Stethoscope } from "lucide-react";
import { useNavigate, useSearchParams } from "react-router";
import InfoField from "@/components/ui/InfoField";
import { DangerButton } from "@/components/ui/button";

const campoClasse =
  "w-full rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 text-base text-slate-700 disabled:cursor-default disabled:opacity-100";

function DetalhesConsulta() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const origemHistorico = searchParams.get("origem") === "historico";

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <Stethoscope size={40} aria-hidden="true" />
        <h1>Detalhes da Consulta</h1>
      </div>

      {/* Dados de exemplo do paciente e da consulta. */}
      <div className="mx-auto flex max-w-4xl flex-col gap-6 p-5 md:p-8">
        <section className="rounded-2xl bg-white p-6 shadow-sm md:p-8">
          <h2 className="mb-6 text-xl font-semibold text-principal">
            Informações do Paciente
          </h2>
          <dl className="flex flex-col gap-5">
            <div className="grid gap-5 md:grid-cols-2">
              <InfoField rotulo="Nome" valor="Monark Igor Power Da Silva" />
              <InfoField rotulo="CPF" valor="123.456.789-00" />
            </div>
            <div className="grid gap-5 md:grid-cols-2">
              <InfoField rotulo="Data de Nascimento" valor="15/08/2018" />
              <InfoField rotulo="Telefone" valor="+55 (31) 99888-7766" />
            </div>
            <InfoField
              rotulo="Endereço"
              valor="Rua das Flores, 123 - Bairro Serra, Belo Horizonte - MG"
            />
            <InfoField rotulo="E-mail para contato" valor="monark.igor@email.com" />
          </dl>
        </section>

        <section className="rounded-2xl bg-white p-6 shadow-sm md:p-8">
          <h2 className="mb-6 text-xl font-semibold text-principal">
            Atendimento e Observações
          </h2>
          <form className="flex flex-col gap-5">
            <dl className="grid gap-5 md:grid-cols-2">
              <InfoField rotulo="Data e Horário" valor="Dia 25 às 19:00" />
              <InfoField
                rotulo="Motivo da Consulta"
                valor="Avaliação de rotina e sintomas gripais"
              />
            </dl>
            <div className="flex flex-col gap-2">
              <label
                htmlFor="observacoes-consulta"
                className="text-sm font-semibold text-slate-700"
              >
                Observações Médicas
              </label>
              <textarea
                id="observacoes-consulta"
                className={`${campoClasse} min-h-32 resize-y`}
                rows={5}
                placeholder="Digite as evoluções clínicas, prescrições ou anotações da consulta..."
                defaultValue="Paciente relata tosse leve há 3 dias. Sem febre nas últimas 24h. Mantém hidratação adequada."
                readOnly={origemHistorico}
              />
            </div>

            {origemHistorico ? (
              <div className="flex justify-end border-t border-slate-200 pt-5">
                <button
                  type="button"
                  onClick={() => navigate(-1)}
                  className="rounded-full bg-principal px-6 py-3 font-semibold text-white transition-colors hover:bg-principal-hover"
                >
                  Voltar para o Histórico
                </button>
              </div>
            ) : (
              <div className="flex flex-col justify-between gap-4 border-t border-slate-200 pt-5 sm:flex-row sm:items-center">
                <DangerButton texto="Cancelar Consulta" />
                <div className="flex flex-col gap-3 sm:flex-row">
                  <button
                    type="button"
                    className="rounded-full border-2 border-destaque px-6 py-3 font-semibold text-slate-700 transition-colors hover:bg-slate-50"
                  >
                    Salvar Observações
                  </button>
                  <button
                    type="button"
                    className="rounded-full bg-emerald-500 px-6 py-3 font-bold text-white transition-colors hover:bg-emerald-600"
                  >
                    Concluir Consulta
                  </button>
                </div>
              </div>
            )}
          </form>
        </section>
      </div>
    </div>
  );
}

export default DetalhesConsulta;
