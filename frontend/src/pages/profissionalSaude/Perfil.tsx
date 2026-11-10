import { useEffect, useRef, useState, type FormEvent } from "react";
import { UserRound } from "lucide-react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { DangerButton, PrimaryButton } from "@/components/ui/button";
import InfoField from "@/components/ui/InfoField";
import StatusAtendimento from "@/components/ui/StatusAtendimento";
import BadgeCategoria from "@/components/ui/BadgeCategoria";

// Dados de exemplo do profissional.
const contatoInicial = {
  telefone: "+55 (31) 98765-4321",
  email: "carlos.sales@meddesk.com",
};

function Perfil() {
  const [contato, setContato] = useState(contatoInicial);
  const [rascunho, setRascunho] = useState(contatoInicial);
  const [foto, setFoto] = useState<string>();
  const [fotoRascunho, setFotoRascunho] = useState<string>();
  const urlsFoto = useRef(new Set<string>());
  const [mensagem, setMensagem] = useState("");
  const [erroFoto, setErroFoto] = useState("");
  const [editando, setEditando] = useState(false);

  useEffect(() => {
    const urls = urlsFoto.current;
    return () => {
      urls.forEach((url) => URL.revokeObjectURL(url));
      urls.clear();
    };
  }, []);

  const liberarFoto = (url: string | undefined) => {
    if (url) {
      URL.revokeObjectURL(url);
      urlsFoto.current.delete(url);
    }
  };

  const salvar = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setContato(rascunho);
    if (foto !== fotoRascunho) liberarFoto(foto);
    setFoto(fotoRascunho);
    setErroFoto("");
    setEditando(false);
    setMensagem("Alterações aplicadas nesta página.");
  };

  const cancelar = () => {
    setRascunho(contato);
    if (fotoRascunho !== foto) liberarFoto(fotoRascunho);
    setFotoRascunho(foto);
    setErroFoto("");
    setEditando(false);
    setMensagem("Alterações não guardadas foram descartadas.");
  };

  return (
    <div>
      <div className="flex items-center gap-3 bg-white p-5 text-3xl font-medium text-principal">
        <UserRound size={40} aria-hidden="true" />
        <h1>O Meu Perfil</h1>
      </div>

      <div className="mx-auto grid max-w-5xl gap-6 p-5 lg:grid-cols-[1fr_2fr] md:p-8">
        <section className="flex min-w-0 flex-col items-center rounded-2xl bg-white p-6 text-center shadow-sm md:p-8">
          <Avatar className="size-36 border-4 border-destaque">
            {fotoRascunho && <AvatarImage src={fotoRascunho} alt="Fotografia do perfil" />}
            <AvatarFallback className="bg-slate-200 text-slate-500">
              <UserRound size={64} aria-hidden="true" />
            </AvatarFallback>
          </Avatar>
          <h2 className="mt-4 text-2xl font-bold text-principal">Dr. Carlos Sales</h2>
          <p className="mb-4 text-slate-600">Pediatra</p>
          <div className="flex flex-wrap justify-center gap-2">
            <BadgeCategoria categoria="Especialista" />
            <StatusAtendimento texto="Pediatria" />
            <StatusAtendimento texto="CRM: 45892-MG" />
          </div>
          {editando && <label className="mt-6 inline-flex cursor-pointer rounded-full border border-destaque px-4 py-2 text-sm font-semibold text-black focus-within:ring-2 focus-within:ring-destaque">
            Alterar Fotografia
            <input
              type="file"
              accept="image/*"
              className="sr-only"
              onChange={(event) => {
                const arquivo = event.target.files?.[0];
                if (arquivo) {
                  if (arquivo.type.startsWith("image/")) {
                    if (fotoRascunho !== foto) liberarFoto(fotoRascunho);
                    const url = URL.createObjectURL(arquivo);
                    urlsFoto.current.add(url);
                    setFotoRascunho(url);
                    setErroFoto("");
                    setMensagem("");
                  } else {
                    setErroFoto("Selecione um arquivo de imagem.");
                  }
                }
                event.target.value = "";
              }}
            />
          </label>}
          {erroFoto && <p role="alert" className="mt-3 text-sm text-red-700">{erroFoto}</p>}
        </section>

        <section className="min-w-0 rounded-2xl bg-white p-6 shadow-sm md:p-8">
          <h2 className="mb-6 text-xl font-semibold text-principal">Informações Pessoais</h2>
          <form onSubmit={salvar}>
            <dl className="grid gap-5 sm:grid-cols-2">
              <InfoField rotulo="Nome" valor="Carlos Eduardo Sales" />
              <InfoField rotulo="Registro profissional" valor="CRM 45892-MG" />
              <InfoField rotulo="Especialidade" valor="Pediatria" />
              <InfoField
                rotulo="Telefone"
                valor={rascunho.telefone}
                tipo="tel"
                editando={editando}
                onChange={(telefone) => {
                  setRascunho((atual) => ({ ...atual, telefone }));
                  setMensagem("");
                }}
              />
              <div className="sm:col-span-2">
                <InfoField
                  rotulo="E-mail para contato"
                  valor={rascunho.email}
                  tipo="email"
                  editando={editando}
                  onChange={(email) => {
                    setRascunho((atual) => ({ ...atual, email }));
                    setMensagem("");
                  }}
                />
              </div>
            </dl>
            {mensagem && <p role="status" className="mt-3 text-sm text-slate-700">{mensagem}</p>}
            <div className="mt-6 flex flex-wrap justify-end gap-3 border-t border-slate-200 pt-5">
              {editando ? (
                <>
                  <DangerButton texto="Cancelar" onClick={cancelar} />
                  <PrimaryButton texto="Guardar Alterações" submit />
                </>
              ) : (
                <PrimaryButton texto="Alterar" onClick={() => {
                  setMensagem("");
                  setEditando(true);
                }} />
              )}
            </div>
          </form>
        </section>
      </div>
    </div>
  );
}

export default Perfil;
