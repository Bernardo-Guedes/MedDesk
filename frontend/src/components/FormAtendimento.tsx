import { ChevronDown } from "lucide-react";
import { PrimaryButton, SecondaryButton } from "./ui/button";

interface FormAtendimentoProps {
    admin?: boolean;
    internacao?: boolean;
}

function FormAtendimento({ admin }: FormAtendimentoProps) {
    return (
        <div className="max-w-200 min bg-white rounded-lg mx-auto mt-8 p-5 flex flex-col gap-5">
            <span className="text-principal font-semibold text-lg">
                {admin ? "Tipo de Atendimento" : "Detalhes do Agendamento"}
            </span>
            <div className="flex justify-between rounded-md bg-background">
                <label className="flex-1 text-principal bg-white m-1 text-center py-2 rounded-md font-semibold">
                    Consulta
                </label>
                <label className="flex-1 text-slate-700 m-1 text-center py-2 font-semibold">
                    Internação
                </label>
            </div>
            <div className="flex w-full flex-col gap-2">
                <label
                    htmlFor="profissional-responsavel"
                    className="text-sm font-semibold text-slate-700"
                >
                    Paciente
                </label>

                <div className="flex w-full items-center rounded-2xl border-2 border-slate-200 bg-slate-50 transition-all duration-200 focus-within:border-cyan-400 focus-within:bg-white focus-within:shadow-[0_0_0_3px_rgba(0,180,216,0.1)]">
                    <input
                        id="profissional-responsavel"
                        type="text"
                        placeholder="Digite para pesquisar o médico..."
                        autoComplete="off"
                        className="min-w-0 flex-1 rounded-2xl bg-transparent px-5 py-3 text-base text-slate-700 outline-none placeholder:text-slate-500"
                    />

                    <ChevronDown
                        size={20}
                        className="mr-5 shrink-0 text-slate-800"
                        aria-hidden="true"
                    />
                </div>
            </div>
            <div className="flex w-full flex-col gap-2">
                <label
                    htmlFor="profissional-responsavel"
                    className="text-sm font-semibold text-slate-700"
                >
                    Profissional Responsável
                </label>

                <div className="flex w-full items-center rounded-2xl border-2 border-slate-200 bg-slate-50 transition-all duration-200 focus-within:border-cyan-400 focus-within:bg-white focus-within:shadow-[0_0_0_3px_rgba(0,180,216,0.1)]">
                    <input
                        id="profissional-responsavel"
                        type="text"
                        placeholder="Digite para pesquisar o médico..."
                        autoComplete="off"
                        className="min-w-0 flex-1 rounded-2xl bg-transparent px-5 py-3 text-base text-slate-700 outline-none placeholder:text-slate-500"
                    />

                    <ChevronDown
                        size={20}
                        className="mr-5 shrink-0 text-slate-800"
                        aria-hidden="true"
                    />
                </div>
            </div>
            <div className="flex justify-between gap-3">
                <div className="flex w-full flex-col gap-2">
                    <label
                        htmlFor="data-consulta"
                        className="text-sm font-semibold text-slate-700"
                    >
                        Data
                    </label>

                    <div className="flex w-full items-center rounded-2xl border-2 border-slate-200 bg-slate-50 transition-all duration-200 focus-within:border-cyan-400 focus-within:bg-white focus-within:shadow-[0_0_0_3px_rgba(0,180,216,0.1)]">
                        <input
                            id="data-consulta"
                            type="date"
                            name="dataConsulta"
                            className="w-full rounded-2xl bg-transparent px-5 py-3 text-base text-slate-700 outline-none"
                        />
                    </div>
                </div>
                <div className="flex w-full flex-col gap-2">
                    <label
                        htmlFor="horario-consulta"
                        className="text-sm font-semibold text-slate-700"
                    >
                        Horário
                    </label>

                    <div className="flex w-full items-center rounded-2xl border-2 border-slate-200 bg-slate-50 transition-all duration-200 focus-within:border-cyan-400 focus-within:bg-white focus-within:shadow-[0_0_0_3px_rgba(0,180,216,0.1)]">
                        <input
                            id="horario-consulta"
                            type="time"
                            name="horarioConsulta"
                            className="w-full rounded-2xl bg-transparent px-5 py-3 text-base text-slate-700 outline-none"
                        />
                    </div>
                </div>
            </div>
            <div className="flex w-full flex-col gap-2">
                <label
                    htmlFor="descricao"
                    className="text-sm font-semibold text-slate-700"
                >
                    Motivo da Consulta
                </label>

                <div className="w-full rounded-2xl border-2 border-slate-200 bg-slate-50 transition-all duration-200 focus-within:border-cyan-400 focus-within:bg-white focus-within:shadow-[0_0_0_3px_rgba(0,180,216,0.1)]">
                    <input
                        id="descricao"
                        type="text"
                        placeholder="Ex: Rotina, dor de cabeça retorno de exames..."
                        autoComplete="off"
                        className="w-full  rounded-2xl bg-transparent px-5 py-3 text-base text-slate-700 outline-none placeholder:text-slate-500"
                    />
                </div>
            </div>
            <div className="flex gap-3 items-center ms-auto">
                <SecondaryButton texto="Cancelar"></SecondaryButton>
                <PrimaryButton texto="Criar atendimento"></PrimaryButton>
            </div>
        </div>
    );
}

export default FormAtendimento;
